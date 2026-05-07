package school.hei.federationagricoleapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.CollectivityActivity;
import school.hei.federationagricoleapi.entity.MemberOccupation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {

    private final Connection connection;

    public List<CollectivityActivity> saveAll(
            String collectivityId,
            List<CollectivityActivity> activities
    ) {

        try (PreparedStatement ps = connection.prepareStatement("""
            INSERT INTO activity (
                id,
                collectivity_id,
                label,
                activity_type,
                executive_date,
                week_ordinal,
                day_of_week,
                member_occupation_concerned
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)
        """)) {

            for (CollectivityActivity a : activities) {

                ps.setString(1, a.getId());
                ps.setString(2, collectivityId);
                ps.setString(3, a.getLabel());
                ps.setString(4, a.getActivityType());

                if (a.getExecutiveDate() != null) {
                    ps.setDate(5, Date.valueOf(a.getExecutiveDate()));
                } else {
                    ps.setNull(5, Types.DATE);
                }

                if (a.getWeekOrdinal() != null) {
                    ps.setInt(6, a.getWeekOrdinal());
                } else {
                    ps.setNull(6, Types.INTEGER);
                }

                if (a.getDayOfWeek() != null) {
                    ps.setString(7, a.getDayOfWeek());
                } else {
                    ps.setNull(7, Types.VARCHAR);
                }

                if (a.getMemberOccupationConcerned() != null) {

                    String[] values = a.getMemberOccupationConcerned()
                            .stream()
                            .map(Enum::name)
                            .toArray(String[]::new);

                    ps.setArray(8,
                            connection.createArrayOf("member_occupation", values)
                    );

                } else {
                    ps.setNull(8, Types.ARRAY);
                }

                ps.addBatch();
            }

            ps.executeBatch();
            return activities;

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting activities", e);
        }
    }

    public List<CollectivityActivity> findAllByCollectivityId(String collectivityId) {

        List<CollectivityActivity> activities = new ArrayList<>();

        try (PreparedStatement ps = connection.prepareStatement("""
            SELECT
                id,
                collectivity_id,
                label,
                activity_type,
                executive_date,
                week_ordinal,
                day_of_week,
                member_occupation_concerned
            FROM activity
            WHERE collectivity_id = ?
        """)) {

            ps.setString(1, collectivityId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                activities.add(map(rs));
            }

            return activities;

        } catch (SQLException e) {
            throw new RuntimeException("Error fetching activities", e);
        }
    }

    private CollectivityActivity map(ResultSet rs) throws SQLException {

        CollectivityActivity a = new CollectivityActivity();

        a.setId(rs.getString("id"));
        a.setCollectivityId(rs.getString("collectivity_id"));
        a.setLabel(rs.getString("label"));
        a.setActivityType(rs.getString("activity_type"));

        Date date = rs.getDate("executive_date");
        if (date != null) {
            a.setExecutiveDate(date.toLocalDate());
        }

        int weekOrdinal = rs.getInt("week_ordinal");
        if (!rs.wasNull()) {
            a.setWeekOrdinal(weekOrdinal);
        }

        a.setDayOfWeek(rs.getString("day_of_week"));

        Array sqlArray = rs.getArray("member_occupation_concerned");

        if (sqlArray != null) {
            String[] values = (String[]) sqlArray.getArray();

            List<MemberOccupation> occupations = new ArrayList<>();

            for (String v : values) {
                occupations.add(MemberOccupation.valueOf(v));
            }

            a.setMemberOccupationConcerned(occupations);
        }

        return a;
    }
}
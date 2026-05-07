package school.hei.federationagricoleapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.CollectivityActivity;
import school.hei.federationagricoleapi.mapper.CollectivityActivityMapper;

import java.sql.*;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {

    private final Connection connection;
    private final CollectivityActivityMapper mapper;

    public List<CollectivityActivity> saveAll(String collectivityId,
                                              List<CollectivityActivity> activities) {

        try (PreparedStatement ps = connection.prepareStatement("""
            INSERT INTO activity
            (id, collectivity_id, label, activity_type,
             executive_date, week_ordinal, day_of_week)
            VALUES (?, ?, ?, ?, ?, ?, ?)
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

                ps.setString(7, a.getDayOfWeek());

                ps.addBatch();
            }

            ps.executeBatch();

            return activities;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
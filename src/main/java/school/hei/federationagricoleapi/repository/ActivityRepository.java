package school.hei.federationagricoleapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.controller.dto.AttendanceStatus;
import school.hei.federationagricoleapi.controller.dto.AttendanceSubmissionDto;
import school.hei.federationagricoleapi.controller.dto.MemberDescription;
import school.hei.federationagricoleapi.controller.dto.MemberOccupation;
import school.hei.federationagricoleapi.entity.CollectivityActivity;
import school.hei.federationagricoleapi.entity.MemberOccupation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ActivityRepository {
    private final Connection connection;

    public boolean activityExists(String activityId, String id) {
        String sql = """
            select 1 from activity a
                     join collectivity c on a.collectivity_id = c.id
                     where a.id = ? and c.id = ?;""";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
           preparedStatement.setString(1, activityId);
           preparedStatement.setString(2, id);
           try(ResultSet resultSet = preparedStatement.executeQuery()){
               return resultSet.next();
           }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getMemberAttendanceStatus(String activityId, String memberId) {
        String sql = "select attendance_status from activity_member_attendance where activity_id = ? and member_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, activityId);
            preparedStatement.setString(2, memberId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("attendance_status");
                }
            }
            return "UNDEFINED";
        }
        catch (SQLException e)  {
            throw new RuntimeException(e);
        }
    }

    public List<AttendanceSubmissionDto> saveAttendance(String activityId, List<AttendanceSubmissionDto> attendances) {
        String sql = """
                insert into activity_member_attendance (id, activity_id, member_id, attendance_status)
                values (?, ?, ?, ?::attendance_status)
                on conflict (activity_id, member_id)
                do nothing
                """;

        String selectSql = """
                select m.id, ama.id as attendance_id, m.first_name, m.last_name,m.email, m.occupation, ama.attendance_status
                    from member m
                join activity_member_attendance ama on m.id = ama.activity_id
                where ama.activity_id = ? and m.id = any(?)
                """;
        try {
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                connection.setAutoCommit(false);

                for (AttendanceSubmissionDto dto : attendances) {
                    String attendanceId = UUID.randomUUID().toString();
                    preparedStatement.setString(1, attendanceId);
                    preparedStatement.setString(2, activityId);
                    preparedStatement.setString(3, dto.getId());
                    preparedStatement.setString(4, dto.getAttendanceStatus().name());
                    preparedStatement.addBatch();
                }

                preparedStatement.executeBatch();
            }

            List<AttendanceSubmissionDto> res = new ArrayList<>();
            try(PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
                String[] memberIds = attendances.stream()
                        .map(AttendanceSubmissionDto::getId)
                        .toArray(String[]::new);
                Array idsArray = connection.createArrayOf("varchar", memberIds);

                preparedStatement.setString(1, activityId);
                preparedStatement.setArray(2, idsArray);

                try(ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        MemberDescription member = new MemberDescription();
                        member.setId(resultSet.getString("id"));
                        member.setFirstName(resultSet.getString("first_name"));
                        member.setLastName(resultSet.getString("last_name"));
                        member.setEmail(resultSet.getString("email"));
                        member.setOccupation(MemberOccupation.valueOf(resultSet.getString("occupation")));

                        AttendanceSubmissionDto resDto = new AttendanceSubmissionDto();
                        resDto.setId(resultSet.getString("attendance_id"));
                        resDto.setMemberDescription(member);
                        resDto.setAttendanceStatus(AttendanceStatus.valueOf(resultSet.getString("attendance_status")));
                        res.add(resDto);
                    }
                }
            }
            connection.commit();
            return res;
        }
        catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }

    public List<AttendanceSubmissionDto> getAllAttendance(String activityId) {
        String sql = """
               select m.id, ama.id as attendance_id, m.first_name, m.last_name,m.email, m.occupation, ama.attendance_status
                    from member m
                join activity_member_attendance ama on m.id = ama.activity_id
                where ama.activity_id = ?
               """;

        List<AttendanceSubmissionDto> res = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, activityId);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    AttendanceSubmissionDto resDto = new AttendanceSubmissionDto();
                    resDto.setId(resultSet.getString("attendance_id"));
                    resDto.setAttendanceStatus(AttendanceStatus.valueOf(resultSet.getString("attendance_status")));

                    MemberDescription member = new MemberDescription();
                    member.setId(resultSet.getString("id"));
                    member.setFirstName(resultSet.getString("first_name"));
                    member.setLastName(resultSet.getString("last_name"));
                    member.setEmail(resultSet.getString("email"));
                    member.setOccupation(MemberOccupation.valueOf(resultSet.getString("occupation")));

                    resDto.setMemberDescription(member);
                    res.add(resDto);
                }
                return res;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

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

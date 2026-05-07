package school.hei.federationagricoleapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.controller.dto.AttendanceStatus;
import school.hei.federationagricoleapi.controller.dto.AttendanceSubmissionDto;
import school.hei.federationagricoleapi.controller.dto.MemberDescription;
import school.hei.federationagricoleapi.controller.dto.MemberOccupation;

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
}

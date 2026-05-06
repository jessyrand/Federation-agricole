package school.hei.federationagricoleapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.controller.dto.MemberDescription;
import school.hei.federationagricoleapi.controller.dto.MemberOccupation;
import school.hei.federationagricoleapi.controller.dto.MemberStats;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CollectivityStatsRepository {
    private final Connection connection;

    public List<MemberStats> fetchCollectivityStats(String id, LocalDate from, LocalDate to) {
        String sql = """
                 select m.id, m.first_name, m.last_name, m.email, m.occupation,
                        (select coalesce(sum(t.amount), 0)
                         from transaction t
                         where t.member_debited_id = m.id
                           and t.transaction_type = 'IN'
                           and t.creation_date between ? and ?) as earned_amount,
                     ((select coalesce(sum(mf.amount), 0)
                       from membership_fee mf
                       where mf.collectivity_id = cm.collectivity_id
                         and mf.status = 'ACTIVE'
                         and mf.eligible_from <= ?)
                          -
                      (select coalesce(sum(t.amount), 0)
                       from transaction t
                       where t.member_debited_id = m.id
                         and t.transaction_type = 'IN')) as unpaid_amount,
                     (select
                          case when count(ama.id) = 0 then 0.0
                              else (count(ama.id) filter (where ama.attendance_status = 'ATTENDED')::float 
                                        / nullif(count(ama.id) filter (where ama.attendance_status in ('ATTENDED', 'MISSING')), 0)) * 100.0
                              end
                      from activity_member_attendance ama
                          join activity a on a.id = ama.activity_id
                      where ama.member_id = m.id
                        and a.executive_date between ? and ?) as assiduity_percentage
                 from member m
                     join collectivity_member cm on m.id = cm.member_id
                 where cm.collectivity_id = ?;
                """;

        List<MemberStats> collectivityStats = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setObject(1, from);
            preparedStatement.setObject(2, to);
            preparedStatement.setObject(3, to);
            preparedStatement.setObject(4, from);
            preparedStatement.setObject(5, to);
            preparedStatement.setString(6, id);

           try (ResultSet resultSet = preparedStatement.executeQuery()){
               while (resultSet.next()) {
                   MemberStats memberStats = new MemberStats();

                   MemberDescription memberDescription = new MemberDescription();
                   memberDescription.setId(resultSet.getString("id"));
                   memberDescription.setFirstName(resultSet.getString("first_name"));
                   memberDescription.setLastName(resultSet.getString("last_name"));
                   memberDescription.setEmail(resultSet.getString("email"));
                   memberDescription.setOccupation(MemberOccupation.valueOf(resultSet.getString("occupation")));

                   memberStats.setMemberDescription(memberDescription);
                   memberStats.setEarnedAmount(resultSet.getDouble("earned_amount"));
                   memberStats.setUnpaidAmount(resultSet.getDouble("unpaid_amount"));
                   memberStats.setAssiduityPercentage(resultSet.getDouble("assiduity_percentage"));

                   collectivityStats.add(memberStats);
               }
           }
           return collectivityStats;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

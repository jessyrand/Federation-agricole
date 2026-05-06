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
            WITH     params AS (
            SELECT
                ?::date AS start_d,
                ?::date AS end_d,
                ?::varchar AS coll_id)
            SELECT m.id, m.first_name, m.last_name, m.email, m.occupation,
                   (SELECT COALESCE(SUM(t.amount), 0)
                    FROM transaction t
                        CROSS JOIN params p
                    WHERE t.member_debited_id = m.id
                      AND t.transaction_type = 'IN'
                      AND t.creation_date BETWEEN p.start_d AND p.end_d) AS earned_amount,
                ((SELECT COALESCE(SUM(
                 CASE
                     WHEN mf.frequency = 'ANNUALLY' THEN mf.amount * (EXTRACT(YEAR FROM p.end_d) - EXTRACT(YEAR FROM p.start_d) + 1)
                     WHEN mf.frequency = 'MONTHLY' THEN mf.amount * ((EXTRACT(YEAR FROM p.end_d) - EXTRACT(YEAR FROM p.start_d)) * 12 +
                                                                     (EXTRACT(MONTH FROM p.end_d) - EXTRACT(MONTH FROM p.start_d)) + 1)
                     WHEN mf.frequency = 'WEEKLY' THEN mf.amount * ((p.end_d - p.start_d) / 7 + 1)
                     ELSE mf.amount
                     END ), 0)
                  FROM membership_fee mf
                      CROSS JOIN params p
                  WHERE mf.collectivity_id = p.coll_id
                    AND mf.status = 'ACTIVE'
                    AND mf.eligible_from <= p.end_d)
                     -
                 (SELECT COALESCE(SUM(t.amount), 0)
                  FROM transaction t
                  WHERE t.member_debited_id = m.id
                    AND t.transaction_type = 'IN')) AS unpaid_amount,
                (SELECT
                     CASE
                         WHEN COUNT(ama.id) = 0 THEN 0.0
                         ELSE (COUNT(ama.id) FILTER (WHERE ama.attendance_status = 'ATTENDED')::float / NULLIF(COUNT(ama.id) FILTER (WHERE ama.attendance_status IN ('ATTENDED', 'MISSING')), 0)) * 100.0
                         end
                 FROM activity_member_attendance ama
                     JOIN activity a ON a.id = ama.activity_id
                     CROSS JOIN params p
                 WHERE ama.member_id = m.id
                   AND a.executive_date BETWEEN p.start_d AND p.end_d) AS assiduity_percentage
            FROM member m
                JOIN collectivity_member cm ON m.id = cm.member_id
                CROSS JOIN params p
            WHERE cm.collectivity_id = p.coll_id;
            """;

        List<MemberStats> collectivityStats = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setObject(1, from);
            preparedStatement.setObject(2, to);
            preparedStatement.setString(3, id);

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

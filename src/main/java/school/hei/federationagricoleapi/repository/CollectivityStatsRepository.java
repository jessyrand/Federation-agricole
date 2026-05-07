package school.hei.federationagricoleapi.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.controller.dto.*;

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

    public List<CollectivityGlobalStatisticsDto> getGlobalStatistics(LocalDate from, LocalDate to) {
        String sql = """
            WITH params AS (
             SELECT
                    ?::date AS start_d,
                    ?::date AS end_d
            ),
                 member_metrics AS (
                     SELECT
                         cm.collectivity_id,
                         cm.member_id,
                         (SELECT COALESCE(SUM(
                                                  CASE
                                                      WHEN mf.frequency = 'ANNUALLY' THEN mf.amount * (EXTRACT(YEAR FROM p.end_d) - EXTRACT(YEAR FROM p.start_d) + 1)
                                                      WHEN mf.frequency = 'MONTHLY' THEN mf.amount * ((EXTRACT(YEAR FROM p.end_d) - EXTRACT(YEAR FROM p.start_d)) * 12 + (EXTRACT(MONTH FROM p.end_d) - EXTRACT(MONTH FROM p.start_d)) + 1)
                                                      WHEN mf.frequency = 'WEEKLY' THEN mf.amount * ((p.end_d - p.start_d) / 7 + 1)
                                                      ELSE mf.amount
                                                      END
                                          ), 0)
                          FROM "membership_fee" mf
                                   CROSS JOIN params p
                          WHERE mf.collectivity_id = cm.collectivity_id
                            AND mf.status = 'ACTIVE'
                            AND mf.eligible_from <= p.end_d) AS expected,
                         (SELECT COALESCE(SUM(t.amount), 0)
                          FROM "transaction" t
                          WHERE t.member_debited_id = cm.member_id
                            AND t.transaction_type = 'IN') AS paid,
                         (SELECT CASE
                                     WHEN COUNT(ama.id) = 0 THEN NULL
                                     ELSE (COUNT(ama.id) FILTER (WHERE ama.attendance_status = 'ATTENDED')::float /
                                           NULLIF(COUNT(ama.id) FILTER (WHERE ama.attendance_status IN ('ATTENDED', 'MISSING')), 0)) * 100.0
                                     END
                          FROM activity_member_attendance ama
                                   JOIN activity a ON a.id = ama.activity_id
                                   CROSS JOIN params p
                          WHERE ama.member_id = cm.member_id
                            AND a.executive_date BETWEEN p.start_d AND p.end_d) AS individual_assiduity
                     FROM "collectivity_member" cm
                              CROSS JOIN params p
                 ),
                 aggregates AS (
                     SELECT
                         collectivity_id,
                         COUNT(*) AS total_members,
                         COUNT(*) FILTER (WHERE (expected - paid) <= 0) AS count_up_to_date,
                         AVG(individual_assiduity) AS avg_assiduity
                     FROM member_metrics
                     GROUP BY collectivity_id
                 ),
                 new_adherents AS (
                     SELECT
                         cm.collectivity_id,
                         COUNT(cm.member_id) AS count
                     FROM "collectivity_member" cm
                              CROSS JOIN params p
                              JOIN "member" m ON m.id = cm.member_id
                     WHERE m.registration_date BETWEEN p.start_d AND p.end_d
                     GROUP BY cm.collectivity_id
                 )
            SELECT
                c.id,
                c.name,
                c.number,
                COALESCE(na.count, 0)::int AS new_member_count,
                CASE
                    WHEN agg.total_members = 0 THEN 0.0
                    ELSE (agg.count_up_to_date::float / agg.total_members) * 100.0
                    END AS overall_due_percentage,
                COALESCE(agg.avg_assiduity, 0.0) AS overall_assiduity_percentage
            FROM "collectivity" c
                     LEFT JOIN aggregates agg ON c.id = agg.collectivity_id
                     LEFT JOIN new_adherents na ON c.id = na.collectivity_id;
            """;
        List<CollectivityGlobalStatisticsDto> statistics = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, from);
            preparedStatement.setObject(2, to);
            try (ResultSet resultSet = preparedStatement.executeQuery()){
                while (resultSet.next()) {
                    CollectivityGlobalStatisticsDto statisticsDto = new CollectivityGlobalStatisticsDto();

                    CollectivityInformation collectivityInformation = new CollectivityInformation();
                    collectivityInformation.setName(resultSet.getString("name"));
                    collectivityInformation.setNumber(resultSet.getInt("number"));

                    statisticsDto.setCollectivityInformation(collectivityInformation);
                    statisticsDto.setNewMemberNumber(resultSet.getInt("new_member_count"));
                    statisticsDto.setOveralMemberCurrentDuePercentage(resultSet.getDouble("overall_due_percentage"));
                    statisticsDto.setOverallMemberAssiduityPercentage(resultSet.getDouble("overall_assiduity_percentage"));

                    statistics.add(statisticsDto);
                }
            }
            return statistics;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

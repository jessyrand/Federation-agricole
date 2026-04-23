package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.ENUM.ActivityStatus;
import school.hei.federationagricoleapi.entity.DTO.CreateMembershipFeeDTO;
import school.hei.federationagricoleapi.entity.ENUM.Frequency;
import school.hei.federationagricoleapi.entity.MembershipFee;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MembershipFeeRepository {

    private final Connection connection;

    public List<MembershipFee> findByCollectivityId(String collectivityId) {

        String sql = """
            SELECT id, eligible_from, frequency, amount, label, status
            FROM membership_fee
            WHERE collectivity_id = ?
            ORDER BY eligible_from DESC
        """;

        List<MembershipFee> fees = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, collectivityId);

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {
                    MembershipFee fee = new MembershipFee();

                    fee.setId(rs.getString("id"));
                    fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                    fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));

                    double amount = rs.getDouble("amount");
                    fee.setAmount(rs.wasNull() ? null : amount);

                    fee.setLabel(rs.getString("label"));
                    fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));

                    fees.add(fee);
                }
            }

            return fees;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<MembershipFee> saveAll(String collectivityId, List<CreateMembershipFeeDTO> dtos) {
        String sql = """
            INSERT INTO membership_fee (
                collectivity_id,
                eligible_from,
                frequency,
                amount,
                label,
                status
            )
            VALUES (?, ?, ?::varchar, ?, ?, 'ACTIVE')
            RETURNING id, eligible_from, frequency, amount, label, status
        """;

        List<MembershipFee> fees = new ArrayList<>();

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            for (CreateMembershipFeeDTO dto : dtos) {

                pstmt.setString(1, collectivityId);
                pstmt.setDate(2, Date.valueOf(dto.getEligibleFrom()));
                pstmt.setString(3, dto.getFrequency().name());
                pstmt.setDouble(4, dto.getAmount());
                pstmt.setString(5, dto.getLabel());

                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        MembershipFee fee = new MembershipFee();
                        fee.setId(rs.getString("id"));
                        fee.setEligibleFrom(rs.getDate("eligible_from").toLocalDate());
                        fee.setFrequency(Frequency.valueOf(rs.getString("frequency")));
                        fee.setAmount(rs.getDouble("amount"));
                        fee.setLabel(rs.getString("label"));
                        fee.setStatus(ActivityStatus.valueOf(rs.getString("status")));

                        fees.add(fee);
                    }
                }
            }

            return fees;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<MembershipFee> findById(String id) {

        String sql = """
        SELECT 
            id,
            eligible_from,
            frequency,
            amount,
            label,
            status
        FROM membership_fee
        WHERE id = ?
    """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {

                    MembershipFee fee = new MembershipFee();

                    fee.setId(rs.getString("id"));
                    fee.setEligibleFrom(
                            rs.getDate("eligible_from").toLocalDate()
                    );
                    fee.setFrequency(
                            Frequency.valueOf(rs.getString("frequency"))
                    );

                    double amount = rs.getDouble("amount");
                    fee.setAmount(rs.wasNull() ? null : amount);

                    fee.setLabel(rs.getString("label"));
                    fee.setStatus(
                            ActivityStatus.valueOf(rs.getString("status"))
                    );

                    return Optional.of(fee);
                }
            }

            return Optional.empty();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

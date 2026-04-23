package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.DTO.CreateMemberPaymentDTO;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;
import school.hei.federationagricoleapi.entity.MemberPayment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


@Repository
@AllArgsConstructor
public class MemberPaymentRepository {

    private Connection connection;

    public MemberPayment save(String memberId, CreateMemberPaymentDTO dto) {

        String sql = """
            INSERT INTO member_payment (
                member_id,
                membership_fee_id,
                amount,
                payment_mode,
                account_credited_id
            )
            VALUES (?, ?, ?, ?::account_type_enum, ?)
            RETURNING id, amount, payment_mode, creation_date
        """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, memberId);
            pstmt.setString(2, dto.getMembershipFeeIdentifier());
            pstmt.setDouble(3, dto.getAmount());
            pstmt.setString(4, dto.getPaymentMode().name());
            pstmt.setString(5, dto.getAccountCreditedIdentifier());

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    MemberPayment payment = new MemberPayment();

                    payment.setId(rs.getString("id"));
                    payment.setAmount(rs.getDouble("amount"));
                    payment.setPaymentMode(
                            AccountType.valueOf(rs.getString("payment_mode"))
                    );
                    payment.setCreationDate(
                            rs.getTimestamp("creation_date").toInstant()
                    );

                    return payment;
                }
            }

            throw new RuntimeException("Insert failed");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

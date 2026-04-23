package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


@Repository
@AllArgsConstructor
public class CollectivityTransactionRepository {
    private Connection connection;

    public void save(String collectivityId,
                     String memberId,
                     double amount,
                     AccountType paymentMode,
                     String accountId) {

        String sql = """
        INSERT INTO collectivity_transaction (
            collectivity_id,
            member_id,
            amount,
            payment_mode,
            account_credited_id
        )
        VALUES (?, ?, ?, ?, ?)
    """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, collectivityId);
            pstmt.setString(2, memberId);
            pstmt.setDouble(3, amount);
            pstmt.setString(4, paymentMode.name());
            pstmt.setString(5, accountId);

            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

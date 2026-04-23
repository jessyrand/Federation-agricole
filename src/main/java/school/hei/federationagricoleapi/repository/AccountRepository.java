package school.hei.federationagricoleapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import school.hei.federationagricoleapi.entity.*;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;
import school.hei.federationagricoleapi.entity.ENUM.BankName;
import school.hei.federationagricoleapi.entity.ENUM.MobilBankingService;
import school.hei.federationagricoleapi.exception.NotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Repository
@AllArgsConstructor
public class AccountRepository {
    private Connection connection;

    public Account findAccountById(String accountId) {
        String sql = """
                select a.id, a.amount, a.type,
                    ba.holder_name as bank_holder_name, ba.bank_name, ba.bank_code, ba.bank_branchcode,
                    ba.bank_account_number, ba.bank_account_key,
                    ma.holder_name as mobil_holder_name, ma.mobil_bank_service, ma.number
                from account a
                left join bank_account ba on ba.id = a.id
                left join mobil_account ma on ma.id = a.id
                where a.id = ?
                """;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setObject(1, UUID.fromString(accountId));
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                   String id =  rs.getString("id");
                   Double amount = rs.getDouble("amount");
                   AccountType type = AccountType.valueOf(rs.getString("type"));

                   if (type.equals(AccountType.MOBILE_BANKING)) {
                       MobileBankingAccount mobileAccount = new MobileBankingAccount();
                       mobileAccount.setId(id);
                       mobileAccount.setAmount(amount);
                       mobileAccount.setMobileNumber(rs.getString("number"));
                       mobileAccount.setMobilBankingService(MobilBankingService.valueOf(rs.getString("mobil_bank_service")));
                       mobileAccount.setHolderName(rs.getString("mobil_holder_name"));
                       return  mobileAccount;
                   } else if (type.equals(AccountType.BANK_TRANSFER)) {
                       BankAccount bankAccount = new BankAccount();
                       bankAccount.setId(id);
                       bankAccount.setAmount(amount);
                       bankAccount.setHolderName(rs.getString("bank_holder_name"));
                       bankAccount.setBankCode(rs.getString("bank_code"));
                       bankAccount.setBankBranchCode(rs.getString("bank_branchcode"));
                       bankAccount.setBankName(BankName.valueOf(rs.getString("bank_name")));
                       bankAccount.setBankAccountNumber(rs.getString("bank_account_number"));
                       bankAccount.setBankAccountKey(rs.getString("bank_account_key"));
                       return bankAccount;
                   }
                   else {
                       return new Account(id, amount);
                   }
                }
                throw new NotFoundException("account not found");
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

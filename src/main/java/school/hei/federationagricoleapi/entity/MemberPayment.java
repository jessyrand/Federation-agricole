package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberPayment {
    private String id;
    private Double amount;
    private AccountType paymentMode;
    private String accountCreditedId;
    private Instant creationDate;
}

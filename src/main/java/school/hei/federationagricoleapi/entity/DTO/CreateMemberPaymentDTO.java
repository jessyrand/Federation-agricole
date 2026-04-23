package school.hei.federationagricoleapi.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMemberPaymentDTO {
    private Double amount;
    private String membershipFeeIdentifier;
    private String accountCreditedIdentifier;
    private AccountType paymentMode;
}
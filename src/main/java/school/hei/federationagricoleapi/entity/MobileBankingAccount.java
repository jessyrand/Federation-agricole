package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.ENUM.MobilBankingService;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MobileBankingAccount extends Account{
    private String holderName;
    private MobilBankingService mobilBankingService;
    private String mobileNumber;
}

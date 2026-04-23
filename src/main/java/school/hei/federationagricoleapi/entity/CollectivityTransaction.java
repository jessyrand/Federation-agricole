package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;
import school.hei.federationagricoleapi.entity.ENUM.Type_enum;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectivityTransaction {
    private String id;
    private String collectivityId;
    private Instant creationDate;
    private AccountType paymentMode;
    private Account acountCredited;
    private Member memberDebited;
    private Type_enum type;
}

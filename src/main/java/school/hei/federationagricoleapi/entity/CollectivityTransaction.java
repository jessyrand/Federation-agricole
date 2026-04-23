package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectivityTransaction {
    private String id;
    private String collectivityId;
    private Instant creationDate;
    private AccountType paymentMode;
    private AcountCredited acountCredited;
    private Member memberDebited;
}

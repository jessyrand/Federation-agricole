package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.ENUM.AccountType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    private String id;
    private Double amount;
    private AccountType type;
}

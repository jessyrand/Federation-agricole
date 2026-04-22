package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MembershipFee {

    private String id;

    private LocalDate eligibleFrom;

    private Frequency frequency;

    private Double amount;

    private String label;

    private ActivityStatus status;
}

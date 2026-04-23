package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.ENUM.ActivityStatus;
import school.hei.federationagricoleapi.entity.ENUM.Frequency;

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

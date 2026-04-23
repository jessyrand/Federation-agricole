package school.hei.federationagricoleapi.entity.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.ENUM.Frequency;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateMembershipFeeDTO {
    private LocalDate eligibleFrom;
    private Frequency frequency;
    private Double amount;
    private String label;
}
package school.hei.federationagricoleapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberStats {
    private MemberDescription memberDescription;
    private Double earnedAmount;
    private Double unpaidAmount;
    private Double assiduityPercentage;
}

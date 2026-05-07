package school.hei.federationagricoleapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectivityGlobalStatisticsDto {
    private CollectivityInformation collectivityInformation;
    private int newMemberNumber;
    private Double overalMemberCurrentDuePercentage;
    private Double overallMemberAssiduityPercentage;
}

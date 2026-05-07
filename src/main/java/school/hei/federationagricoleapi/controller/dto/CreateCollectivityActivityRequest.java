package school.hei.federationagricoleapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCollectivityActivityRequest {
    private String label;
    private String activityType;

    private LocalDate executiveDate;

    private Integer weekOrdinal;
    private String dayOfWeek;

}

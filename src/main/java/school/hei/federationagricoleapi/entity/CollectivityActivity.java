package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CollectivityActivity {

    private String id;
    private String collectivityId;

    private String label;
    private String activityType;

    private LocalDate executiveDate;

    private Integer weekOrdinal;
    private String dayOfWeek;

}
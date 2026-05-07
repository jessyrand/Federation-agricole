package school.hei.federationagricoleapi.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CollectivityActivity {

    private String id;
    private String collectivityId;

    private String label;
    private String activityType;

    private List<MemberOccupation> memberOccupationConcerned;

    private LocalDate executiveDate;

    private Integer weekOrdinal;
    private String dayOfWeek;
}
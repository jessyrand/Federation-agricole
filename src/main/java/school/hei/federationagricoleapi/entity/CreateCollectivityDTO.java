package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectivityDTO {
    private String location;
    private boolean federationApproval;
    private String president;
    private String vicePresident;
    private String treasurer;
    private String secretary;
}
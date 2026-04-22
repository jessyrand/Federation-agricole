package school.hei.federationagricoleapi.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectivityDTO {
    private String location;
    private boolean federationApproval;
    private String vicePresident;
    private String treasurer;
    private String secretary;
}
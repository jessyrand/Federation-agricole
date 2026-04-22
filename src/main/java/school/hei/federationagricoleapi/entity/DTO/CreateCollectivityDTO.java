package school.hei.federationagricoleapi.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectivityDTO {
    private String location;
    private Boolean federationApproval;
    private String[] member_id;
    private StructureDTO structure;
}
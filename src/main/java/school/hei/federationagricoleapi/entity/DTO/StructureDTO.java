package school.hei.federationagricoleapi.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StructureDTO {
    private String President_id;
    private String vicePresident_id;
    private String treasurer_id;
    private String secretary_id;
}

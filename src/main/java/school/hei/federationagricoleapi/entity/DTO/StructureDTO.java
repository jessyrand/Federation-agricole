package school.hei.federationagricoleapi.entity.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StructureDTO {
    private String president;
    private String vicePresident;
    private String treasurer;
    private String secretary;
}

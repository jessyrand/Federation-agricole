package school.hei.federationagricoleapi.entity.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateCollectivityDTO {
    private String location;
    private Boolean federationApproval;
    private List<String> members;
    private StructureDTO structure;
}
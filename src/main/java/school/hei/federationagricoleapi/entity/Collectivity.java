package school.hei.federationagricoleapi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Collectivity {
    private String id;

    private String location;

    private CollectivityStructure structure;

    private List<Member> members;
}

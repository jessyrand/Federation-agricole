package school.hei.federationagricoleapi.validator;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;

import java.util.List;

@Component
public class CollectivityValidator {
    public void collectivityValidator(List<CreateCollectivityDTO> createCollectivityDTO) throws BadRequestException {
        for (CreateCollectivityDTO createCollectivity : createCollectivityDTO) {
            if (!createCollectivity.getFederationApproval()
                    || createCollectivity.getStructure().getPresident_id() == null || createCollectivity.getStructure().getPresident_id().isEmpty()
                    || createCollectivity.getStructure().getVicePresident_id() == null || createCollectivity.getStructure().getVicePresident_id().isEmpty()
                    || createCollectivity.getStructure().getTreasurer_id() == null || createCollectivity.getStructure().getTreasurer_id().isEmpty()
                    || createCollectivity.getStructure().getSecretary_id() == null || createCollectivity.getStructure().getSecretary_id().isEmpty()
            ) {
                throw new BadRequestException("Collectivity without federation approval or structure missing.");
            }
        }
    }
}

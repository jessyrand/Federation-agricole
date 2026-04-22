package school.hei.federationagricoleapi.validator;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;

import java.util.List;

@Component
public class CollectivityValidator {
    public void collectivityValidator(List<CreateCollectivityDTO> createCollectivityDTO) throws BadRequestException {
        for (CreateCollectivityDTO createCollectivity : createCollectivityDTO) {
            if (!createCollectivity.getAuthorization()
                    || createCollectivity.getPresident_id() == null || createCollectivity.getPresident_id().isEmpty()
                    || createCollectivity.getVicePresident_id() == null || createCollectivity.getVicePresident_id().isEmpty()
                    || createCollectivity.getTreasurer_id() == null || createCollectivity.getTreasurer_id().isEmpty()
                    || createCollectivity.getSecretary_id() == null || createCollectivity.getSecretary_id().isEmpty()
            ) {
                throw new BadRequestException("Collectivity without federation approval or structure missing.");
            }
        }
    }
}

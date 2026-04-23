package school.hei.federationagricoleapi.validator;

import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.entity.DTO.StructureDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.exception.BadRequestException;

import java.util.List;

@Component
public class CollectivityValidator {
    public void collectivityValidator(List<CreateCollectivityDTO> createCollectivityDTO) throws BadRequestException {
        for (CreateCollectivityDTO createCollectivity : createCollectivityDTO) {
            if (Boolean.FALSE.equals(createCollectivity.getFederationApproval())) {
                System.out.println("FD");
                throw new BadRequestException("Collectivity without federation approval or structure missing.");
            }

            StructureDTO structure = createCollectivity.getStructure();
            if (structure == null) {
                System.out.println("Struct");
                throw new BadRequestException("Collectivity without federation approval or structure missing.");
            }

            if (isNullOrEmpy(structure.getPresident()) || isNullOrEmpy(structure.getVicePresident())
                || isNullOrEmpy(structure.getTreasurer()) ||  isNullOrEmpy(structure.getSecretary())) {
                System.out.println("sec:" + structure.getSecretary());
                System.out.println("tres:" + structure.getTreasurer());
                System.out.println("vc:" + structure.getVicePresident());
                System.out.println("pr:" + structure.getPresident());
                throw new BadRequestException("Collectivity without federation approval or structure missing.");
            }
        }
    }

    private boolean isNullOrEmpy(String str) {
        return str == null || str.isEmpty();
    }
}

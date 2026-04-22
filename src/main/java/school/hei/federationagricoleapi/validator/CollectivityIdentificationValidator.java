package school.hei.federationagricoleapi.validator;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CollectivityIdentificationDTO;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;

@Component
public class CollectivityIdentificationValidator {
    private CollectivityRepository collectivityRepository;

    public CollectivityIdentificationValidator(CollectivityRepository collectivityRepository) {
        this.collectivityRepository = collectivityRepository;
    }

    public void validateIdentification(Collectivity collectivity) throws BadRequestException {
        if (collectivity == null) {
            throw new NotFoundException("Collectivity not found");
        }

        if (collectivity.getNumber() != null || collectivity.getName() != null) {
            throw new BadRequestException("Collectivity already identified");
        }

        if (collectivityRepository.existsByName(collectivity.getName())) {
            throw new IllegalStateException("Name already exists");
        }
    }
}

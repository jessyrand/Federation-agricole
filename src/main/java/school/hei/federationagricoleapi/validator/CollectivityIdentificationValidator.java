package school.hei.federationagricoleapi.validator;

import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.DTO.CollectivityIdentificationDTO;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;

@Component
public class CollectivityIdentificationValidator {
    private CollectivityRepository collectivityRepository;
    public void validateIdentification(CollectivityIdentificationDTO dto) throws BadRequestException {
        if (dto == null) {
            throw new NotFoundException("Collectivity not found");
        }

        if (dto.getNumber() != null || dto.getName() != null) {
            throw new BadRequestException("Collectivity already identified");
        }

        if (collectivityRepository.existsByName(dto.getName())) {
            throw new IllegalStateException("Name already exists");
        }
    }
}

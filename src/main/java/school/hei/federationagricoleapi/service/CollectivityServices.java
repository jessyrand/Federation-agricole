package school.hei.federationagricoleapi.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CollectivityIdentificationDTO;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.MemberRepository;
import school.hei.federationagricoleapi.validator.CollectivityIdentificationValidator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CollectivityServices {
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;
    private CollectivityIdentificationValidator collectivityIdentificationValidator;

    public List<Collectivity> createColectivity(List<CreateCollectivityDTO> collectivities) {
        List<Collectivity> collectivitiesList = collectivityRepository.save(collectivities);

        return collectivitiesList;
    }

    public Collectivity identifyCollectivity(CollectivityIdentificationDTO dto)
            throws NotFoundException, BadRequestException {

        if (dto.getId() == null || dto.getNumber() == null || dto.getName() == null) {
            throw new BadRequestException("Missing required fields");
        }

        Collectivity collectivity = collectivityRepository.findById(dto.getId()).orElse(null);
        collectivityIdentificationValidator.validateIdentification(collectivity);


        collectivity.setNumber(dto.getNumber());
        collectivity.setName(dto.getName());

        return collectivityRepository.updateIdentification(
                collectivity.getId(),
                collectivity.getNumber(),
                collectivity.getName()
        );
    }
}

package school.hei.federationagricoleapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.MemberRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CollectivityServices {
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public List<Collectivity> createColectivity(List<CreateCollectivityDTO> collectivities) {
        List<Collectivity> collectivitiesList = collectivityRepository.save(collectivities);

        return collectivitiesList;
    }
}

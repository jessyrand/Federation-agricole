package school.hei.federationagricoleapi.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.MemberRepository;

@Service
@AllArgsConstructor
public class CollectivityServices {
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public Collectivity createColectivity(CreateCollectivityDTO collectivity) {
        String id = collectivityRepository.save(collectivity);

        return collectivityRepository.getById(id);
    }
}

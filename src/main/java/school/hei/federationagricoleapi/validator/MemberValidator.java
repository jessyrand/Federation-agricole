package school.hei.federationagricoleapi.validator;

import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.MemberRepository;

import java.util.List;

@Component
public class MemberValidator {
    private MemberRepository memberRepository;

    public void memberValidation(List<CreateCollectivityDTO> collectivities) {
        for (CreateCollectivityDTO collectivity : collectivities) {
            for (String id : collectivity.getMember_id()) {
                if (memberRepository.findById(id).isEmpty()) {
                    throw new NotFoundException("Not found");
                }
            }
        }
    }
}

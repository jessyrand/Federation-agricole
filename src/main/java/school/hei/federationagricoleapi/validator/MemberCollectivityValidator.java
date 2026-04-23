package school.hei.federationagricoleapi.validator;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.repository.MemberRepository;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
@AllArgsConstructor
public class MemberCollectivityValidator {
    private MemberRepository memberRepository;

    public void memberCountValidator(List<CreateCollectivityDTO> createCollectivity) throws BadRequestException {
        int minMembers = 10;
        long memberCount = 0;
        for (CreateCollectivityDTO collectivity : createCollectivity) {
            for (String member : collectivity.getMembers()) {
                memberCount++;
            }
        }

        if (memberCount < minMembers) {
            throw new BadRequestException("Member must be 10 minimum");
        }
    }

    public void seniorCountValidator(List<Collectivity> createCollectivity) throws BadRequestException {
        int minSenior = 5;
        Instant sixMonth = Instant.now().minus(180, ChronoUnit.DAYS);
        long seniorCount = 0;

        for (Collectivity collectivity : createCollectivity) {
            for (Member member : collectivity.getMembers()) {
                if (member.getCreatedAt().isBefore(sixMonth)) {
                    seniorCount++;
                }
            }
        }

        if (seniorCount < minSenior) {
            throw new BadRequestException("New collectivity must have at least five members with six or more month of membership.");
        }
    }

}

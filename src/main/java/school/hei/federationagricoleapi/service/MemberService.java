package school.hei.federationagricoleapi.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateMemberDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final CollectivityRepository collectivityRepository;

    public MemberService(MemberRepository memberRepository,
                         CollectivityRepository collectivityRepository) {
        this.memberRepository = memberRepository;
        this.collectivityRepository = collectivityRepository;
    }

    public List<Member> createMembers(List<CreateMemberDTO> requests) {

        List<Member> result = new ArrayList<>();

        for (CreateMemberDTO req : requests) {

            if (!req.getRegistrationFeePaid() || !req.getMembershipDuesPaid()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Fees not paid");
            }

            UUID collectivityId = UUID.fromString(req.getCollectivityIdentifier());
            Collectivity collectivity = collectivityRepository.findById(collectivityId);

            if (collectivity == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Collectivity not found");
            }

            List<UUID> refereeIds = new ArrayList<>();

            if (req.getReferees() != null) {
                for (String ref : req.getReferees()) {

                    UUID refId = UUID.fromString(ref);

                    if (!memberRepository.existsMember(refId)) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid referee");
                    }

                    refereeIds.add(refId);
                }
            }

            UUID memberId = memberRepository.insertMember(req);

            for (UUID refId : refereeIds) {
                memberRepository.insertReferee(memberId, refId);
            }

            Member member = memberRepository.findFullMember(memberId, collectivity);

            result.add(member);
        }

        return result;
    }
}

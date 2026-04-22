package school.hei.federationagricoleapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateMemberDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.MemberRepository;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CollectivityRepository collectivityRepository;

    public List<Member> createMembers(List<CreateMemberDTO> dtos) {

        List<Member> members = new ArrayList<>();

        for (CreateMemberDTO dto : dtos) {

            if (Boolean.FALSE.equals(dto.getRegistrationFeePaid()) ||
                    Boolean.FALSE.equals(dto.getMembershipDuesPaid())) {
                throw new IllegalArgumentException("Fees not paid");
            }

            Collectivity collectivity = collectivityRepository
                    .findById(dto.getCollectivityIdentifier())
                    .orElseThrow(() -> new NotFoundException("Collectivity not found"));

            List<Member> referees = new ArrayList<>();
            if (dto.getReferees() != null) {
                for (String refereeId : dto.getReferees()) {
                    Member referee = memberRepository.findById(refereeId)
                            .orElseThrow(() -> new NotFoundException("Referee not found"));
                    referees.add(referee);
                }
            }

            Member member = new Member();
            member.setFirstName(dto.getFirstName());
            member.setLastName(dto.getLastName());
            member.setBirthDate(dto.getBirthDate());
            member.setGender(dto.getGender());
            member.setAddress(dto.getAddress());
            member.setProfession(dto.getProfession());
            member.setPhoneNumber(String.valueOf(dto.getPhoneNumber())); // ⚠️ conversion
            member.setEmail(dto.getEmail());
            member.setOccupation(dto.getOccupation());
            member.setCreatedAt(Instant.now());

            member.setCollectivity(collectivity);
            member.setReferees(referees);

            members.add(member);
        }

        return memberRepository.saveAll(members);
    }
}
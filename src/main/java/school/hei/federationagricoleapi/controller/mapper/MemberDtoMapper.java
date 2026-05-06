package school.hei.federationagricoleapi.controller.mapper;

import school.hei.federationagricoleapi.controller.dto.CreateMember;
import school.hei.federationagricoleapi.controller.dto.MemberOccupation;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.Gender;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDtoMapper {
    private final CollectivityRepository collectivityRepository;
    private final MemberRepository memberRepository;

    public Member mapToEntity(CreateMember createMemberDto) {
        Optional<Collectivity> optionalCollectivity = collectivityRepository.findById(createMemberDto.getCollectivityIdentifier());

        if (optionalCollectivity.isEmpty()) {
            throw new NotFoundException("Collectivity.id=" + createMemberDto.getCollectivityIdentifier() + " not found");
        }

        var refereeMembers = createMemberDto.getReferees().stream()
                .map(refereeId -> {
                    var memberReferee = memberRepository.findById(refereeId).orElseThrow(
                            () -> new NotFoundException("Member.id=" + refereeId + "not found"));
                    memberReferee.addCollectivity(optionalCollectivity.get());
                    return memberReferee;
                })
                .toList();

        var member = Member.builder()
                .firstName(createMemberDto.getFirstName())
                .lastName(createMemberDto.getLastName())
                .birthDate(createMemberDto.getBirthDate())
                .gender(createMemberDto.getGender() == null ? null : Gender.valueOf(createMemberDto.getGender().name()))
                .occupation(createMemberDto.getOccupation() == null ? null : school.hei.federationagricoleapi.entity.MemberOccupation.valueOf(createMemberDto.getOccupation().name()))
                .address(createMemberDto.getAddress())
                .profession(createMemberDto.getProfession())
                .phoneNumber(createMemberDto.getPhoneNumber())
                .email(createMemberDto.getEmail())
                .registrationFeePaid(createMemberDto.getRegistrationFeePaid())
                .membershipDuesPaid(createMemberDto.getMembershipDuesPaid())
                .build();

        member.addCollectivity(optionalCollectivity.get());
        member.addReferees(refereeMembers);

        return member;
    }

    public school.hei.federationagricoleapi.controller.dto.Member mapToDto(Member member) {
        if (member == null) {
            return null;
        }
        return school.hei.federationagricoleapi.controller.dto.Member.builder()
                .id(member.getId())
                .firstName(member.getFirstName())
                .lastName(member.getLastName())
                .birthDate(member.getBirthDate())
                .address(member.getAddress())
                .profession(member.getProfession())
                .phoneNumber(member.getPhoneNumber())
                .profession(member.getProfession())
                .email(member.getEmail())
                .gender(member.getGender() == null ? null : school.hei.federationagricoleapi.controller.dto.Gender.valueOf(member.getGender().name()))
                .occupation(member.getOccupation() == null ? null : MemberOccupation.valueOf(member.getOccupation().name()))
                .referees(member.getReferees() == null ? List.of() : member.getReferees().stream()
                        .map(this::mapToDto)
                        .toList())
                .build();
    }
}

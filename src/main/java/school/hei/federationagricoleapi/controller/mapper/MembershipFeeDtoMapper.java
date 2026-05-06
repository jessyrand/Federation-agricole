package school.hei.federationagricoleapi.controller.mapper;

import school.hei.federationagricoleapi.controller.dto.ActivityStatus;
import school.hei.federationagricoleapi.controller.dto.CreateMembershipFee;
import school.hei.federationagricoleapi.controller.dto.Frequency;
import school.hei.federationagricoleapi.controller.dto.MembershipFee;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MembershipFeeDtoMapper {
    public MembershipFee mapToDto(school.hei.federationagricoleapi.entity.MembershipFee membershipFee) {
        return MembershipFee.builder()
                .id(membershipFee.getId())
                .label(membershipFee.getLabel())
                .amount(membershipFee.getAmount())
                .frequency(membershipFee.getFrequency() == null ? null : Frequency.valueOf(membershipFee.getFrequency().name()))
                .status(membershipFee.getStatus() == null ? null : ActivityStatus.valueOf(membershipFee.getStatus().name()))
                .eligibleFrom(membershipFee.getEligibleFrom())
                .build();
    }

    public school.hei.federationagricoleapi.entity.MembershipFee mapToEntity(CreateMembershipFee createMembershipFee) {
        return school.hei.federationagricoleapi.entity.MembershipFee.builder()
                .label(createMembershipFee.getLabel())
                .amount(createMembershipFee.getAmount())
                .frequency(createMembershipFee.getFrequency() == null ? null : school.hei.federationagricoleapi.entity.Frequency.valueOf(createMembershipFee.getFrequency().name()))
                .eligibleFrom(createMembershipFee.getEligibleFrom())
                .build();
    }
}

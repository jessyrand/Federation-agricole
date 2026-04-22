package school.hei.federationagricoleapi.service;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.entity.DTO.CreateMembershipFeeDTO;
import school.hei.federationagricoleapi.entity.MembershipFee;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.MembershipFeeRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class MembershipFeeService {

    private final MembershipFeeRepository repository;
    private final CollectivityRepository collectivityRepository;

    public List<MembershipFee> createFees(String collectivityId, List<CreateMembershipFeeDTO> dtos)
            throws BadRequestException {

        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity not found"));

        for (CreateMembershipFeeDTO dto : dtos) {
            if (dto.getAmount() == null || dto.getAmount() <= 0) {
                throw new BadRequestException("Amount must be > 0");
            }

            if (dto.getFrequency() == null) {
                throw new BadRequestException("Frequency is required");
            }
        }

        return repository.saveAll(collectivityId, dtos);
    }
}

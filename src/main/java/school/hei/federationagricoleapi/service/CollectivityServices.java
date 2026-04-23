package school.hei.federationagricoleapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.CollectivityTransaction;
import school.hei.federationagricoleapi.entity.DTO.CollectivityIdentificationDTO;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.MemberRepository;
import school.hei.federationagricoleapi.validator.CollectivityIdentificationValidator;

import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class CollectivityServices {
    private CollectivityRepository collectivityRepository;
    private MemberRepository memberRepository;
    private CollectivityRepository.TransactionRepository transactionRepository;
    private CollectivityIdentificationValidator collectivityIdentificationValidator;

    public List<Collectivity> createColectivity(List<CreateCollectivityDTO> collectivities) {
        List<Collectivity> collectivitiesList = collectivityRepository.save(collectivities);
        for (Collectivity collectivity : collectivitiesList) {
            for (Member member : collectivity.getMembers()) {
                if (memberRepository.findById(member.getId()).isEmpty()) {
                    throw new NotFoundException("Member not found");
                }
            }
        }
        return collectivitiesList;
    }
    public Collectivity identifyCollectivity(String id, CollectivityIdentificationDTO dto)
            throws NotFoundException, BadRequestException {

        if (id == null || dto.getNumber() == null || dto.getName() == null) {
            throw new BadRequestException("Missing required fields");
        }

        Collectivity collectivity = collectivityRepository.findById(id).orElse(null);
        collectivityIdentificationValidator.validateIdentification(collectivity);


        collectivity.setNumber(dto.getNumber());
        collectivity.setName(dto.getName());

        return collectivityRepository.updateIdentification(
                collectivity.getId(),
                collectivity.getNumber(),
                collectivity.getName()
        );
    }

    public List<CollectivityTransaction> getTransactions (String collectivityId, Instant from, Instant to) {
        if (from == null || to == null) {
            throw new BadRequestException("Either mandatory 'to' or 'from' param not provided");
        }
        if (from.isAfter(to)) {
            throw new BadRequestException("Invalid date range");
        }

        collectivityRepository.findById(collectivityId)
                .orElseThrow(() -> new NotFoundException("Collectivity not found"));

        return transactionRepository.findByCollectivityIdAndDateBetween(collectivityId, from, to);
    }
}

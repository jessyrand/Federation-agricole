package school.hei.federationagricoleapi.controller;

import school.hei.federationagricoleapi.controller.dto.*;
import school.hei.federationagricoleapi.controller.mapper.CollectivityDtoMapper;
import school.hei.federationagricoleapi.controller.mapper.FinancialAccountDtoMapper;
import school.hei.federationagricoleapi.controller.mapper.MembershipFeeDtoMapper;
import school.hei.federationagricoleapi.controller.mapper.TransactionDtoMapper;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.CollectivityActivity;
import school.hei.federationagricoleapi.entity.MembershipFee;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.repository.CollectivityStatsRepository;
import school.hei.federationagricoleapi.service.ActivityService;
import school.hei.federationagricoleapi.service.CollectivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class CollectivityController {
    private final CollectivityDtoMapper collectivityDtoMapper;
    private final MembershipFeeDtoMapper membershipFeeDtoMapper;
    private final CollectivityService collectivityService;
    private final FinancialAccountDtoMapper financialAccountDtoMapper;
    private final TransactionDtoMapper transactionDtoMapper;
    private final CollectivityStatsRepository collectivityStatsRepository;
    private final CollectivityRepository collectivityRepository;
    private final ActivityService activityService;

    @GetMapping("/collectivities/{id}")
    public ResponseEntity<?> getCollectivityById(@PathVariable String id) {
        try {
            return ResponseEntity.status(OK).body(collectivityDtoMapper.mapToDto(collectivityService.getCollectivityById(id)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }

    }

    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivity(@RequestBody List<CreateCollectivity> createCollectivities) {
        try {
            List<Collectivity> collectivities = createCollectivities.stream()
                    .map(collectivityDtoMapper::mapToEntity)
                    .toList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(collectivityService.createCollectivities(collectivities).stream()
                            .map(collectivityDtoMapper::mapToDto)
                            .toList());
        } catch (
                BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (
                NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/collectivities/{id}/informations")
    public ResponseEntity<?> updateCollectivityInformation(@PathVariable String id,
                                                           @RequestBody CollectivityInformation collectivityInformation) {
        String name = collectivityInformation.getName();
        Integer number = collectivityInformation.getNumber();
        try {
            return ResponseEntity.status(OK)
                    .body(collectivityDtoMapper.mapToDto(collectivityService.updateInformations(id, name, number)));
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> getCollectivityMembershipFeesByCollectivity(@PathVariable String id) {
        try {
            return ResponseEntity.status(OK)
                    .body(collectivityService.getMembershipFeesByCollectivityIdentifier(id).stream()
                            .map(membershipFeeDtoMapper::mapToDto)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> createCollectivityMembershipFee(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFee> membershipFees) {
        try {
            List<MembershipFee> membershipFeesToCreate = membershipFees.stream()
                    .map(membershipFeeDtoMapper::mapToEntity)
                    .toList();
            return ResponseEntity.status(OK)
                    .body(collectivityService.createMembershipFees(id, membershipFeesToCreate).stream()
                            .map(membershipFeeDtoMapper::mapToDto)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/financialAccounts")
    public ResponseEntity<?> getCollectivityFinancialAccounts(@PathVariable String id,
                                                              @RequestParam(required = false) LocalDate at) {
        try {
            return ResponseEntity.status(OK)
                    .body(collectivityService.getFinancialAccounts(id).stream()
                            .map(financialAccount -> financialAccountDtoMapper.mapToDto(financialAccount, at))
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/transactions")
    public ResponseEntity<?> getCollectivityTransactions(@PathVariable String id, @RequestParam LocalDate from, @RequestParam LocalDate to) {
        try {
            return ResponseEntity.status(OK)
                    .body(collectivityService.getTransactionsByCollectivity(id, from, to).stream()
                            .map(transactionDtoMapper::mapToDto)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/statistics")
    public ResponseEntity<?> getCollectivityStatistics(@PathVariable String id, @RequestParam LocalDate from, @RequestParam LocalDate to) {
        try {
            List<MemberStats> stats = collectivityService.getCollectivityStats(id, from, to);
            return ResponseEntity.status(OK)
                    .body(stats);
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/statistics")
    public ResponseEntity<?> getCollectivityStatistics(@RequestParam LocalDate from, @RequestParam LocalDate to) {
        try {
            List<CollectivityGlobalStatisticsDto> statistics = collectivityService.getGlobalStatistics(from, to);

            if (statistics.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Collectivity statistics not found");
            }
            return ResponseEntity.status(HttpStatus.OK)
                    .body(statistics);
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/{id}/activities")
    public ResponseEntity<List<CollectivityActivity>> create(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivityRequest> requests
    ) {
        return ResponseEntity.status(201)
                .body(activityService.create(id, requests));
    }
}

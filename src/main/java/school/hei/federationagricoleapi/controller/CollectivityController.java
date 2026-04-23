package school.hei.federationagricoleapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.federationagricoleapi.entity.Account;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.CollectivityTransaction;
import school.hei.federationagricoleapi.entity.DTO.CollectivityIdentificationDTO;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;

import school.hei.federationagricoleapi.entity.DTO.CreateMembershipFeeDTO;
import school.hei.federationagricoleapi.entity.MembershipFee;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.service.CollectivityServices;
import school.hei.federationagricoleapi.service.MembershipFeeService;
import school.hei.federationagricoleapi.validator.CollectivityValidator;
import school.hei.federationagricoleapi.validator.MemberCollectivityValidator;

import java.time.Instant;
import java.util.List;

@RestController
@AllArgsConstructor
public class CollectivityController {
    private MemberCollectivityValidator memberCollectivityValidator;
    private CollectivityServices collectivityServices;
    private CollectivityValidator collectivityValidator;
    private MembershipFeeService membershipFeeService;


    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivities(@RequestBody List<CreateCollectivityDTO> collectivities) {
        try {
            collectivityValidator.collectivityValidator(collectivities);
            memberCollectivityValidator.memberCountValidator(collectivities);
            List<Collectivity> collectivitiesList = collectivityServices.createColectivity(collectivities);
            memberCollectivityValidator.seniorCountValidator(collectivitiesList);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(collectivitiesList);
        }
        catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/collectivities/{id}/informations")
    public ResponseEntity<?> identifyCollectivity(
            @PathVariable String id,
            @RequestBody CollectivityIdentificationDTO dto
    ) {
        try {
            Collectivity collectivity = collectivityServices.identifyCollectivity(id, dto);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(collectivity);
        }
        catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        catch (IllegalStateException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> getMembershipFees(@PathVariable String id) {
        try {
            List<MembershipFee> fees = membershipFeeService.getFeesByCollectivity(id);
            return ResponseEntity.ok(fees);
        }
        catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/collectivities/{id}/membershipFees")
    public ResponseEntity<?> createMembershipFees(
            @PathVariable String id,
            @RequestBody List<CreateMembershipFeeDTO> dtos
    ) {
        try {
            List<MembershipFee> fees = membershipFeeService.createFees(id, dtos);

            return ResponseEntity.ok(fees);
        }
        catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/transactions")
    public ResponseEntity<?> getTransactions(
            @PathVariable String id,
            @RequestParam Instant from,
            @RequestParam Instant to
    ) {
        try {
            List<CollectivityTransaction> transactions = collectivityServices.getTransactions(id, from, to);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(transactions);
        }
        catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
        catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/collectivities/{id}/financialAccounts")
    public ResponseEntity<?> getFinancialAccounts(
            @PathVariable String id,
            @RequestParam (required = false) String at) {
        try {
            Instant atInstant = (at == null || at.isBlank() )? null : Instant.parse(at);

            List<Account> accounts = collectivityServices.getFinancialAccount(id, atInstant);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(accounts);
        }
        catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
        catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}

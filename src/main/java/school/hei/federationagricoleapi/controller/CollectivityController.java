package school.hei.federationagricoleapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CollectivityIdentificationDTO;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.service.CollectivityServices;
import school.hei.federationagricoleapi.validator.CollectivityIdentificationValidator;
import school.hei.federationagricoleapi.validator.CollectivityValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class CollectivityController {
    private CollectivityServices collectivityServices;
    private CollectivityValidator collectivityValidator;
    private CollectivityIdentificationValidator collectivityIdentificationValidator;

    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivities(@RequestBody List<CreateCollectivityDTO> collectivities) {
        try {
            collectivityValidator.collectivityValidator(collectivities);
            List<Collectivity> collectivitiesList = collectivityServices.createColectivity(collectivities);

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

    @PutMapping("/collectivities/identify")
    public ResponseEntity<?> identifyCollectivity(@RequestBody CollectivityIdentificationDTO dto) {
        try {
            collectivityIdentificationValidator.validateIdentification(dto);
            Collectivity collectivity = collectivityServices.identifyCollectivity(dto);

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
}

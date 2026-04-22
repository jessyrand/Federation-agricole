package school.hei.federationagricoleapi.controller;

import lombok.AllArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.federationagricoleapi.entity.Collectivity;
import school.hei.federationagricoleapi.entity.DTO.CreateCollectivityDTO;
import school.hei.federationagricoleapi.service.CollectivityServices;
import school.hei.federationagricoleapi.validator.CollectivityValidator;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class CollectivityController {
    private CollectivityServices collectivityServices;
    private CollectivityValidator collectivityValidator;

    @PostMapping("/collectivities")
    public ResponseEntity<?> createCollectivities(@RequestBody List<CreateCollectivityDTO> collectivities) {
        try {
            collectivityValidator.collectivityValidator(collectivities);
            List<Collectivity> collectivitiesList = collectivityServices.createColectivity(collectivities);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(collectivitiesList);
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
}

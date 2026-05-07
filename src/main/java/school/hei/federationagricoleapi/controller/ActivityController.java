package school.hei.federationagricoleapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.federationagricoleapi.controller.dto.CreateCollectivityActivityRequest;
import school.hei.federationagricoleapi.entity.CollectivityActivity;
import school.hei.federationagricoleapi.service.ActivityService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/collectivities")
public class ActivityController {

    private final ActivityService service;

    @PostMapping("/{id}/activities")
    public ResponseEntity<List<CollectivityActivity>> create(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivityRequest> requests
    ) {
        return ResponseEntity.status(201)
                .body(service.create(id, requests));
    }
}
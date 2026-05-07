package school.hei.federationagricoleapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.federationagricoleapi.controller.dto.AttendanceSubmissionDto;
import school.hei.federationagricoleapi.controller.dto.CreateActivityMemberAttendance;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.controller.dto.CreateCollectivityActivityRequest;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.mapper.CollectivityActivityMapper;
import school.hei.federationagricoleapi.service.ActivityService;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/collectivities")
public class ActivityController {
    private final ActivityService activityService;
    private final CollectivityActivityMapper collectivityActivityMapper;

    @PostMapping("/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> submitAttendance(
            @PathVariable String id,
            @PathVariable String activityId,
            @RequestBody List<CreateActivityMemberAttendance> attendanceList
    ) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(activityService.saveAttendance(id, activityId, attendanceList));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
          }
    }
  
    @PostMapping("/{id}/activities")
    public ResponseEntity<?> createActivities(
            @PathVariable String id,
            @RequestBody List<CreateCollectivityActivityRequest> requests
    ) {
        try {
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(
                            activityService.create(id, requests)
                    );

        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/{id}/activities/{activityId}/attendance")
    public ResponseEntity<?> getAttendance(
            @PathVariable String id,
            @PathVariable String activityId
    ) {
        try {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(activityService.getAttendances(id, activityId));
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }
    
    @GetMapping("/{id}/activities")
    public ResponseEntity<?> getActivities(@PathVariable String id) {
        try {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(
                            activityService.getAllByCollectivityId(id)
                    );

        } catch (BadRequestException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());

        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());

        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}

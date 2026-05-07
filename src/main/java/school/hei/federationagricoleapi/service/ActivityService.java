package school.hei.federationagricoleapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.controller.dto.AttendanceSubmissionDto;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.ActivityRepository;
import school.hei.federationagricoleapi.repository.CollectivityRepository;
import school.hei.federationagricoleapi.controller.dto.CreateCollectivityActivityRequest;
import school.hei.federationagricoleapi.entity.CollectivityActivity;
import school.hei.federationagricoleapi.repository.ActivityRepository;
import school.hei.federationagricoleapi.repository.CollectivityRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final CollectivityRepository collectivityRepository;

    public List<AttendanceSubmissionDto> saveAttendance(String id, String activityId, List<AttendanceSubmissionDto> attendanceList) {
        if (collectivityRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Collectivity with id " + id + " not found");
        }
        if (!activityRepository.activityExists(id, activityId)) {
            throw new NotFoundException("Activity with id " + activityId + " not found");
        }
        if (attendanceList.isEmpty()) {
            throw new BadRequestException("Attendance list is empty");
        }

        return activityRepository.saveAttendance(activityId, attendanceList);
    }

    public List<AttendanceSubmissionDto> getAttendances(String id, String activityId) {
        if (collectivityRepository.findById(id).isEmpty()) {
            throw new NotFoundException("Collectivity with id " + id + " not found");
        }
        return activityRepository.getAllAttendance(activityId);
    }

    public List<CollectivityActivity> create(
            String collectivityId,
            List<CreateCollectivityActivityRequest> requests
    ) {

        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new RuntimeException("Collectivity not found");
        }

        List<CollectivityActivity> activities = new ArrayList<>();

        for (CreateCollectivityActivityRequest r : requests) {

            validate(r);

            CollectivityActivity a = new CollectivityActivity();
            a.setId(r.getId());
            a.setCollectivityId(collectivityId);

            a.setLabel(r.getLabel());
            a.setActivityType(r.getActivityType());

            a.setMemberOccupationConcerned(r.getMemberOccupationConcerned());

            a.setExecutiveDate(r.getExecutiveDate());
            a.setWeekOrdinal(r.getWeekOrdinal());
            a.setDayOfWeek(r.getDayOfWeek());

            activities.add(a);
        }

        return repository.saveAll(collectivityId, activities);
    }

    private void validate(CreateCollectivityActivityRequest r) {

        if (r.getId() == null || r.getId().isBlank()) {
            throw new RuntimeException("Activity id is required");
        }

        boolean hasDate = r.getExecutiveDate() != null;
        boolean hasRecurrence = r.getWeekOrdinal() != null || r.getDayOfWeek() != null;

        if (hasDate && hasRecurrence) {
            throw new RuntimeException("Cannot mix executiveDate and recurrenceRule");
        }

        if (!hasDate && !hasRecurrence) {
            throw new RuntimeException("Activity must have either executiveDate or recurrenceRule");
        }

        if (r.getMemberOccupationConcerned() == null ||
                r.getMemberOccupationConcerned().isEmpty()) {
            throw new RuntimeException("memberOccupationConcerned is required");
        }
    }

    public List<CollectivityActivity> getAllByCollectivityId(String collectivityId) {

        if (collectivityRepository.findById(collectivityId).isEmpty()) {
            throw new RuntimeException("Collectivity not found");
        }

        return repository.findAllByCollectivityId(collectivityId);
    }
}

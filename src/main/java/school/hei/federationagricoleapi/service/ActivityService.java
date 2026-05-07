package school.hei.federationagricoleapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.hei.federationagricoleapi.controller.dto.AttendanceSubmissionDto;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.repository.ActivityRepository;
import school.hei.federationagricoleapi.repository.CollectivityRepository;

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
}

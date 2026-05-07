package school.hei.federationagricoleapi.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceSubmissionDto {
    private String id;
    private MemberDescription memberDescription;
    private AttendanceStatus AttendanceStatus;
}

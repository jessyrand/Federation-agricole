package school.hei.federationagricoleapi.entity.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import school.hei.federationagricoleapi.entity.Gender;
import school.hei.federationagricoleapi.entity.MemberOccupation;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMemberDTO {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Gender gender;
    private String address;
    private String profession;
    private Integer phoneNumber;
    private String email;
    private MemberOccupation occupation;
    private String collectivityIdentifier;
    private String[] referees;
    private Boolean registrationFeePaid;
    private Boolean membershipDuesPaid;
}

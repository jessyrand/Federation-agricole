package school.hei.federationagricoleapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.hei.federationagricoleapi.entity.DTO.CreateMemberDTO;
import school.hei.federationagricoleapi.entity.DTO.CreateMemberPaymentDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.entity.MemberPayment;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.service.MemberPaymentService;
import school.hei.federationagricoleapi.service.MemberService;

import java.util.List;

@RestController
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MemberPaymentService memberPaymentService;

    @PostMapping("/members")
    public ResponseEntity<List<Member>> createMembers(@RequestBody List<CreateMemberDTO> membersDTO) {
        List<Member> members = memberService.createMembers(membersDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(members);
    }

    @PostMapping("/members/{id}/payments")
    public ResponseEntity<?> createPayments(
            @PathVariable String id,
            @RequestBody List<CreateMemberPaymentDTO> dtos
    ) {
        try {
            List<MemberPayment> payments = memberPaymentService.createPayments(id, dtos);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(payments);

        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
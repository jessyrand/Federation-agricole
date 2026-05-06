package school.hei.federationagricoleapi.controller;

import org.springframework.web.bind.annotation.PathVariable;
import school.hei.federationagricoleapi.controller.dto.CreateMember;
import school.hei.federationagricoleapi.controller.dto.CreateMemberPayment;
import school.hei.federationagricoleapi.controller.mapper.MemberDtoMapper;
import school.hei.federationagricoleapi.controller.mapper.MemberPaymentDtoMaper;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.entity.MemberPayment;
import school.hei.federationagricoleapi.exception.BadRequestException;
import school.hei.federationagricoleapi.exception.NotFoundException;
import school.hei.federationagricoleapi.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final MemberDtoMapper memberDtoMapper;
    private final MemberPaymentDtoMaper memberPaymentDtoMaper;

    @PostMapping("/members")
    public ResponseEntity<?> createMembers(@RequestBody List<CreateMember> createMemberDtos) {
        try {
            List<Member> convertedCreateMembers = createMemberDtos.stream()
                    .map(memberDtoMapper::mapToEntity)
                    .toList();

            List<Member> savedMembers = memberService.addNewMembers(convertedCreateMembers);

            return ResponseEntity.status(HttpStatus.OK)
                    .body(savedMembers.stream()
                            .map(memberDtoMapper::mapToDto)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PostMapping("/members/{id}/payments")
    public ResponseEntity<?> createMemberPayments(@PathVariable String id, @RequestBody List<CreateMemberPayment> createMemberPayments) {
        try {
            List<MemberPayment> memberPayments = createMemberPayments.stream()
                    .map(createMemberPayment -> memberPaymentDtoMaper.mapToEntity(id, createMemberPayment))
                    .toList();
            return ResponseEntity.status(HttpStatus.OK)
                    .body(memberService.createPayments(memberPayments).stream()
                            .map(memberPaymentDtoMaper::mapToDto)
                            .toList());
        } catch (BadRequestException e) {
            return ResponseEntity.status(BAD_REQUEST)
                    .body(e.getMessage());
        } catch (NotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}

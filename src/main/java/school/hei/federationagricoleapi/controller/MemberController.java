package school.hei.federationagricoleapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.federationagricoleapi.entity.DTO.CreateMemberDTO;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/members")
@AllArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<List<Member>> createMembers(@RequestBody List<CreateMemberDTO> membersDTO) {
        List<Member> members = memberService.createMembers(membersDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(members);
    }
}
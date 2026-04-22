package school.hei.federationagricoleapi.controller;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import school.hei.federationagricoleapi.entity.Member;
import school.hei.federationagricoleapi.service.MemberService;

import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService service;

    public MemberController(MemberService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<List<Member>> createMembers(@RequestBody List<CreateMember> request) {
        return ResponseEntity
                .status(HttpStatusCode.valueOf(201))
                .body(service.createMembers(request));
    }
}

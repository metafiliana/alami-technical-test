package dev.metafiliana.alamitechnicaltest.member.handler;

import dev.metafiliana.alamitechnicaltest.member.entities.Member;
import dev.metafiliana.alamitechnicaltest.member.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MemberHandler {
    @Autowired
    private MemberService memberSvc;

    @GetMapping("/members")
    public ResponseEntity<List<Member>> getAllMember(
            @RequestParam(name = "name",
                    required = false,
                    defaultValue = "") String name) {
        List<Member> members;
        members = memberSvc.getMember(name);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }

    @PostMapping("/members")
    public ResponseEntity<Member> create(
            @RequestBody Member member) {
        Member newMember = memberSvc.createMember(member);
        return new ResponseEntity<>(newMember, HttpStatus.CREATED);
    }
}

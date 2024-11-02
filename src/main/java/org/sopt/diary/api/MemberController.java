package org.sopt.diary.api;

import jakarta.servlet.http.HttpServletRequest;
import org.sopt.diary.exception.MemberNotFoundException;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.Member;
import org.sopt.diary.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/signup")
    public ResponseEntity<MemberIdResponce> createMember(@RequestBody CreateMemberRequest request) {
        final Member member = new Member(request.getName(), request.getNickname(), request.getAge());
        Long id = this.memberService.createMember(member);
        MemberIdResponce response = new MemberIdResponce(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/members/login")
    public ResponseEntity<MemberIdResponce> loginMember(@RequestBody MemberIdRequest request) {
        try {
            Long id = this.memberService.findMember(request.getMemberId());
            return ResponseEntity.ok()
                    .header("memberId", String.valueOf(id)) // 헤더에 ID 추가
                    .body(new MemberIdResponce(id));
        } catch (MemberNotFoundException e) {
            return ResponseEntity.badRequest().body(new MemberIdResponce()); // 400 Bad Request
        }
    }

}

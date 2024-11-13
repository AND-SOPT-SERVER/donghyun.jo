package org.sopt.diary.service;

import org.sopt.diary.exception.MemberNotFoundException;
import org.sopt.diary.repository.MemberEntity;
import org.sopt.diary.repository.MemberRepository;

import java.util.Optional;

public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Long createMember(Member member) {
        final MemberEntity memberEntity = new MemberEntity(member);
        return memberRepository.save(memberEntity).getId();
    }

    public Long findMember(Long memberId) {
        Optional<MemberEntity> foundMember= memberRepository.findById(memberId);
        if (foundMember.isPresent()) {
            return foundMember.get().getId();
        }
        throw new MemberNotFoundException();
    }
}

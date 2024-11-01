package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.sopt.diary.service.Member;

@Entity
public class MemberEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private int age;

    public MemberEntity(Member member) {
        this.id = member.getId();
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.age = member.getAge();
    }

    public MemberEntity() {}
}

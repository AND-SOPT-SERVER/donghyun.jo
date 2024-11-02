package org.sopt.diary.api;


class CreateMemberRequest {
    private String name;
    private String nickname;
    private int age;

    public CreateMemberRequest(String name, String nickname, int age) {
        this.name = name;
        this.nickname = nickname;
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public String getNickname() {
        return nickname;
    }

    public String getName() {
        return name;
    }
}

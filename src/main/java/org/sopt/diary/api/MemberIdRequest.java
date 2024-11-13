package org.sopt.diary.api;

class MemberIdRequest {
    private Long memberId;
    MemberIdRequest(Long id) {
        this.memberId = id;
    }
    MemberIdRequest() {}
    Long getMemberId() {
        return memberId;
    }
}

package org.sopt.diary.api;


class DiaryResponce {
    private Long diaryId;
    private String title;

    DiaryResponce(Long diaryId, String title) {
        this.diaryId = diaryId;
        this.title = title;
    }


    Long getId() {
        return diaryId;
    }

    String getTitle() {
        return title;
    }

}

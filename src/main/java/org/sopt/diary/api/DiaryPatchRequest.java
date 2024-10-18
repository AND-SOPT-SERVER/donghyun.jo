package org.sopt.diary.api;

class DiaryPatchRequest {
    Long diaryId;
    String title;
    String content;
    String category;

    public Long getDiaryId() {
        return diaryId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCategory() {
        return category;
    }
}

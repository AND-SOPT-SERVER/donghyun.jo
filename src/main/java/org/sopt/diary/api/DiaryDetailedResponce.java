package org.sopt.diary.api;

import java.time.LocalDateTime;

class DiaryDetailedResponce {
    private Long diaryId;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    DiaryDetailedResponce(Long id, String title, LocalDateTime createdAt, String content) {
        this.diaryId = id;
        this.title = title;
        this.createdAt = createdAt;
        this.content = content;
    }

    public DiaryDetailedResponce() {
    }

    Long getId() {
        return diaryId;
    }

    String getTitle() {
        return title;
    }

    String getContent() {
        return content;
    }

    LocalDateTime getCreatedAt() {
        return createdAt;
    }
}

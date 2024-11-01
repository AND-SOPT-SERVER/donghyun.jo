package org.sopt.diary.service;

import org.sopt.diary.exception.DiaryTooLongException;

import java.time.LocalDateTime;

public class Diary {
    private Long id;
    private final String title;
    private String content;
    private LocalDateTime createdAt;
    private Category category;

    public Diary(String title, String content, LocalDateTime createdAt, Category category) {
        this.title = title;
        this.content = content;
        this.createdAt = createdAt;
        this.category = category;
    }

    public Diary(Long id, String title, String content, LocalDateTime createdAt, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        vaildateLengthOfBody();
        this.createdAt = createdAt;
        this.category = category;
    }

    public Diary(Long id, String title) {
        this.id = id;
        this.title = title;
    }


    public void vaildateLengthOfBody() {
        if (this.content.length() > 30){
            throw new DiaryTooLongException();
        }
    }

    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public Category getCategory() {
        return category;
    }
}

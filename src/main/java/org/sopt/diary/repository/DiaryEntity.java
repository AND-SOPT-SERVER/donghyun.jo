package org.sopt.diary.repository;

import jakarta.persistence.*;
import org.sopt.diary.service.Category;
import org.sopt.diary.service.Diary;

import java.time.LocalDateTime;

@Entity
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(nullable = false)
    public String title;

    @Column(nullable = false)
    public String content;

    @Column(nullable = false)
    public int lengthOfBody;

    @Column(nullable = false)
    public LocalDateTime createdAt;

    @Column(nullable = false)
    public Category category;

    public DiaryEntity(Diary diary) {
        this.title = diary.getTitle();
        this.content = diary.getContent();
        this.lengthOfBody = diary.getLengthOfBody();
        this.createdAt = diary.getCreatedAt();
        this.category = diary.getCategory();
    }

    public DiaryEntity() {

    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }

    public int getLengthOfBody() {
        return lengthOfBody;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCategory(String category) {
        this.category = Category.valueOf(category);
    }
}

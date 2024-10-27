package org.sopt.diary.api;

import org.sopt.diary.service.Category;

class CreateDiaryRequest {
    private String title;
    private String content;
    private Category category;

    public CreateDiaryRequest(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public Category getCategory() {
        return category;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }
}

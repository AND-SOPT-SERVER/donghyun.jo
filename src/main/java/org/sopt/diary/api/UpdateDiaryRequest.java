package org.sopt.diary.api;

import org.sopt.diary.service.Category;

class UpdateDiaryRequest {
    private Long id;
    private String title;
    private String content;
    private Category category;

    UpdateDiaryRequest(Long id, String title, String content, Category category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }

    Long getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getContent() {
        return content;
    }

    Category getCategory() {
        return category;
    }
}

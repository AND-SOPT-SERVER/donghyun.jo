package org.sopt.diary.api;

import java.util.List;

class DiaryListResponce {
    private List<DiaryResponce> diaries;

    DiaryListResponce(List<DiaryResponce> diaries) {
        this.diaries = diaries;
    }

    List<DiaryResponce> getDiaries() {
        return diaries;
    }
}

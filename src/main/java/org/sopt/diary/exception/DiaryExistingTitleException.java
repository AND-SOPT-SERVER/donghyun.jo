package org.sopt.diary.exception;

public class DiaryExistingTitleException extends RuntimeException{
    public DiaryExistingTitleException(String title) {
        super("Diary title \"" + title + "\" already exists.");
    }
}

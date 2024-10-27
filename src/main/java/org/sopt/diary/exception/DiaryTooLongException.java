package org.sopt.diary.exception;

public class DiaryTooLongException extends RuntimeException{
    public DiaryTooLongException() {
        super("Diary title must not exceed 30 characters.");
    }
}

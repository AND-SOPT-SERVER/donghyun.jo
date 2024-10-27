package org.sopt.diary.exception;

public class DiaryNotFoundException extends RuntimeException{
    public DiaryNotFoundException(Long id) {
        super("Diary id \"" + id + "\" not exists.");
    }
}
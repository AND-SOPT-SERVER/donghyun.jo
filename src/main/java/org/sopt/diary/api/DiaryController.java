package org.sopt.diary.api;


import org.sopt.diary.exception.DiaryNotFoundException;
import org.sopt.diary.exception.DiaryTooLongException;
import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DiaryController {
    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/diaries")
    public ResponseEntity<DiaryIdResponce> createDiary(@RequestBody CreateDiaryRequest request) {
        try {
            Diary diary = new Diary(request.getTitle(), request.getContent(), LocalDateTime.now(), request.getCategory());
            Long id = this.diaryService.createDiary(diary);
            DiaryIdResponce response = new DiaryIdResponce(id);
            return ResponseEntity.ok(response);
        } catch (DiaryTooLongException e) {
            return ResponseEntity.badRequest().body(new DiaryIdResponce()); // 400 Bad Request
        }
    }


    @GetMapping("/diaries/List")
    public ResponseEntity<DiaryListResponce> getDiaryList(
            @RequestParam(value = "category", defaultValue = "none") String category,
            @RequestParam(value = "orderBy", defaultValue = "ASC") String orderBy) {
        final OrderBy order = OrderBy.getValue(orderBy);
        List<Diary> diaries = this.diaryService.getDiaryList(category, order);
        final List<DiaryResponce> diaryResponces = new ArrayList<>();
        for (Diary diary : diaries) {
            diaryResponces.add(new DiaryResponce(diary.getId(), diary.getTitle()));
        }
        return ResponseEntity.ok(new DiaryListResponce(diaryResponces));
    }

    // 페이징 구현
    @GetMapping("/diaries/page")
    public ResponseEntity<DiaryListResponce> getDiaryPage(
            @RequestParam int lastContentLength,
            @RequestParam LocalDateTime lastCreatedAt,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        List<Diary> diaries = this.diaryService.getDiaryByPage(lastContentLength, lastCreatedAt, pageable);

        final List<DiaryResponce> diaryResponces = new ArrayList<>();
        for (Diary diary : diaries) {
            diaryResponces.add(new DiaryResponce(diary.getId(), diary.getTitle()));
        }
        return ResponseEntity.ok(new DiaryListResponce(diaryResponces));
    }


    @GetMapping("/diaries")
    public ResponseEntity<DiaryDetailedResponce> getDiary(@RequestBody DiaryIdResponce request){
        try {
            Diary diary = this.diaryService.findDiary(request.getDiaryId());
            DiaryDetailedResponce responce = new DiaryDetailedResponce(diary.getId(),diary.getTitle(),diary.getCreatedAt(),diary.getContent());
            return ResponseEntity.ok(responce);
        } catch (DiaryNotFoundException e) {
            return ResponseEntity.badRequest().body(new DiaryDetailedResponce());
        }
    }


    @DeleteMapping("/diaries")
    public ResponseEntity<DiaryIdResponce> deleteDiary(@RequestParam DiaryIdRequest request){
        try {
            Long id = request.diaryId;
            id = this.diaryService.deleteDiary(id);
            DiaryIdResponce responce = new DiaryIdResponce(id);
            return ResponseEntity.ok(responce);
        } catch (DiaryNotFoundException e) {
            return ResponseEntity.badRequest().body(new DiaryIdResponce());
        }
    }

    @PatchMapping("/diaries")
    public ResponseEntity<DiaryIdResponce> patchDiary(@RequestBody UpdateDiaryRequest request){
        try {
            Diary diary = new Diary(request.getId(), request.getTitle(), request.getContent(), LocalDateTime.now(), request.getCategory());
            this.diaryService.updateDiary(diary);
            return ResponseEntity.ok(new DiaryIdResponce(diary.getId()));
        } catch (DiaryTooLongException e) {
            return ResponseEntity.badRequest().body(new DiaryIdResponce()); // 400 Bad Request
        }
    }
}
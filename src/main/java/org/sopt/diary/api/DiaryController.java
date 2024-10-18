package org.sopt.diary.api;


import org.sopt.diary.service.Diary;
import org.sopt.diary.service.DiaryService;
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
        Diary diary = new Diary(request.title, request.content, LocalDateTime.now(), request.category);
        if (diary.isValid()){
            throw new IllegalArgumentException("Diary Content length Exceeded");
        }
        Long id = this.diaryService.createDiary(diary);
        DiaryIdResponce responce = new DiaryIdResponce(id);
        return ResponseEntity.ok(responce);
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

    @GetMapping("/diaries")
    public ResponseEntity<DiaryDetailedResponce> getDiary(@RequestBody DiaryIdResponce request){
        Diary diary = this.diaryService.findDiary(request.getDiaryId());
        DiaryDetailedResponce responce = new DiaryDetailedResponce(diary.getId(),diary.getTitle(),diary.getCreatedAt(),diary.getContent());
        return ResponseEntity.ok(responce);
    }


    @DeleteMapping("/diaries")
    public ResponseEntity<DiaryIdResponce> deleteDiary(@RequestParam DiaryIdRequest request){
        Long id = request.diaryId;
        id = this.diaryService.deleteDiary(id);
        DiaryIdResponce responce = new DiaryIdResponce(id);
        return ResponseEntity.ok(responce);

    }

    @PatchMapping("/diaries")
    public ResponseEntity<DiaryIdResponce> patchDiary(@RequestParam DiaryIdRequest request){
        Long id = request.diaryId;
        id = this.diaryService.deleteDiary(id);
        DiaryIdResponce responce = new DiaryIdResponce(id);
        return ResponseEntity.ok(responce);
    }
}
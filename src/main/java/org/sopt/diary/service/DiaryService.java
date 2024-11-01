package org.sopt.diary.service;
import org.sopt.diary.api.OrderBy;
import org.sopt.diary.exception.DiaryExistingTitleException;
import org.sopt.diary.exception.DiaryNotFoundException;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Long createDiary(Diary diary) {
        boolean existsByTitle = diaryRepository.existsByTitle(diary.getTitle());
        if (existsByTitle) {
            throw new DiaryExistingTitleException(diary.getTitle());
        }
        DiaryEntity diaryEntity = diaryRepository.save(new DiaryEntity(diary));
        return diaryEntity.getId();
    }

    public Long updateDiary(Diary diary) {
        Optional<DiaryEntity> diaryEntity = diaryRepository.findById(diary.getId());
        boolean existsByTitle = diaryRepository.existsByTitle(diary.getTitle());
        if (existsByTitle) {
            throw new DiaryExistingTitleException(diary.getTitle());
        }
        if (diaryEntity.isPresent()){
            DiaryEntity tempDiary = diaryEntity.get();
            tempDiary.setTitle(diary.getTitle());
            tempDiary.setContent(diary.getContent());
            tempDiary.setCategory(diary.getCategory().toString());
        }
        return diaryEntity.get().getId();
    }

    public Long deleteDiary(Long id) {
        try{
            diaryRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new DiaryNotFoundException(id);
        }
        return id;
    }

    public Diary findDiary(Long id) {
        Optional<DiaryEntity> diaryEntity = diaryRepository.findById(id);
        if (diaryEntity.isPresent()){
            DiaryEntity tempDiary = diaryEntity.get();
            Diary diary = new Diary(tempDiary.getId(), tempDiary.title,tempDiary.content,tempDiary.getCreatedAt(), tempDiary.getCategory());
            return diary;
        }
        throw new DiaryNotFoundException(id);
    }

    public List<Diary> getDiaryList(String category, OrderBy orderBy) {
        final List<Diary> diaries = new ArrayList<>();
        final List<DiaryEntity> diaryEntities;
        if (category.equals("none")){
            if (orderBy == OrderBy.ASC) {
                diaryEntities = diaryRepository.findAllOrderByBodyLengthAsc();
            }
            else {
                diaryEntities = diaryRepository.findAllOrderByBodyLengthDesc();
            }
        }
        else {
            if (orderBy == OrderBy.ASC) {
                diaryEntities = diaryRepository.findByCategoryOrderByBodyLengthAsc(Category.valueOf(category));
            }
            else {
                diaryEntities = diaryRepository.findByCategoryOrderByBodyLengthDesc(Category.valueOf(category));
            }
        }
        for (DiaryEntity diaryEntity : diaryEntities) {
            diaries.add(new Diary(diaryEntity.getId(), diaryEntity.getTitle()));
        }
        return diaries;
    }

}

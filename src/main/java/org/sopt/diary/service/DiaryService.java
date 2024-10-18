package org.sopt.diary.service;
import org.sopt.diary.api.OrderBy;
import org.sopt.diary.repository.DiaryEntity;
import org.sopt.diary.repository.DiaryRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Component
public class DiaryService {
    private final DiaryRepository diaryRepository;

    public DiaryService(DiaryRepository diaryRepository) {
        this.diaryRepository = diaryRepository;
    }

    public Long createDiary(Diary diary) {
        DiaryEntity diaryEntity = diaryRepository.save(new DiaryEntity(diary));
        return diaryEntity.getId();
    }

    public Long updateDiary(Diary diary) {
        Optional<DiaryEntity> diaryEntity = diaryRepository.findById(diary.getId());
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
            throw new IllegalArgumentException("Diary does not exist");
        }
        return id;
    }

    public Diary findDiary(Long id) {
        Optional<DiaryEntity> diaryEntity = diaryRepository.findById(id);
        if (diaryEntity.isPresent()){
            DiaryEntity tempDiary = diaryEntity.get();
            Diary diary = new Diary(tempDiary.getId(),tempDiary.title,tempDiary.content,tempDiary.getCreatedAt());
            return diary;
        }
        throw new IllegalArgumentException("Diary does not exist");
    }

    public List<Diary> getDiaryList(String category, OrderBy orderBy) {
        List<DiaryEntity> diaryEntities = diaryRepository.findAll();
        diaryEntities = filterByCategory(diaryEntities, category);
        diaryEntities = orderDiaryList(diaryEntities, orderBy);
        final List<Diary> diaries = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntities) {
            diaries.add(new Diary(diaryEntity.getId(), diaryEntity.getTitle()));
        }
        return diaries;
    }

    private List<DiaryEntity> filterByCategory(List<DiaryEntity> diaryEntities, String category){
        final List<DiaryEntity> filterdDiaries = new ArrayList<>();
        for (DiaryEntity diaryEntity : diaryEntities) {
            if (!category.equals("none") || diaryEntity.getCategory() == Category.valueOf(category)){
                filterdDiaries.add(diaryEntity);
            }
        }
        return filterdDiaries;
    }

    private List<DiaryEntity> orderDiaryList(List<DiaryEntity> diaryEntities, OrderBy orderBy) {
        final List<DiaryEntity> filteredDiaries = new ArrayList<>(diaryEntities); // 원본 리스트 복사

        if (orderBy == OrderBy.DESC) {
            filteredDiaries.sort(Comparator.comparingInt(DiaryEntity::getLengthOfBody).reversed());
        } else if (orderBy == OrderBy.ASC) {
            filteredDiaries.sort(Comparator.comparingInt(DiaryEntity::getLengthOfBody));
        }
        return filteredDiaries;
    }

}

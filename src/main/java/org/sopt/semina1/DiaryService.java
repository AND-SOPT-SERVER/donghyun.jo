package org.sopt.semina1;

import java.time.LocalDate;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();
    private LocalDate lastUpdatedDate = LocalDate.now();
    private int updateCount = 0;

    void writeDiary(final String body) {
        final Diary diary = new Diary(null, body);
        diaryRepository.save(diary);
    }

    public List<Diary> getDiaries() {
        return diaryRepository.findAll();
    }

    public void delete(String id) {
        diaryRepository.deleteOne(Long.parseLong(id));
    }

    public Diary restore(String id) {
        String body = diaryRepository.restore(Long.parseLong(id));
        final Diary diary = new Diary(Long.parseLong(id), body);
        return diary;
    }

    public void update(String id, String body){
        Long diaryId = Long.parseLong(id);
        LocalDate today = LocalDate.now();
        if (!today.equals(lastUpdatedDate)) {
            lastUpdatedDate = today;
        }
        updateCount++;
        if (updateCount < 2) {
            diaryRepository.putOne(diaryId, body);
        } else {
            throw new IllegalStateException("only 2 times per day");
        }
    }
}

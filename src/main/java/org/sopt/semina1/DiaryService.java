package org.sopt.semina1;

import java.time.LocalDate;
import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();
    private LocalDate lastUpdatedDate = LocalDate.now();
    private int updateCount = 0;
    final static int MAX_LENGTH = 30;

    void writeDiary(final String body) {
        // 이모지나 다른거 다 글자수 한개 차지
        if(body.codePointCount(0, body.length()) > MAX_LENGTH){
            throw new IllegalArgumentException("Diary content too long");
        }
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

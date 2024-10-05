package org.sopt.semina1;

import java.util.List;

public class DiaryService {
    private final DiaryRepository diaryRepository = new DiaryRepository();
        public DiaryService() {

        }

        void writeDiary(final String body){
            final Diary diary = new Diary(null, body);
            diaryRepository.save(diary);
        }

        public List<Diary> getDiaries(){
            return diaryRepository.findAll();
        }

        public void delete(String id){
            diaryRepository.deleteOne(Long.parseLong(id));
        }

        public void update(String id, String body){
            diaryRepository.putOne(Long.parseLong(id), body);
        }



}
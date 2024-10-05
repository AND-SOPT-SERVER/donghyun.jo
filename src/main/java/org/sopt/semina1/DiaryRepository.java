package org.sopt.semina1;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class DiaryRepository {
    private final Map<Long, String> storage = new ConcurrentHashMap<>();
    private final AtomicLong numbering = new AtomicLong();

    public DiaryRepository() {
    }

    void save(final Diary diary){
        final Long id = numbering.addAndGet(1);
        storage.put(id, diary.getBody());
    }

    void putOne(Long id, String body){
        storage.put(id, body);
    }

    void deleteOne(Long id){
        storage.remove(id);
    }

    List<Diary> findAll(){
        return LongStream.rangeClosed(1, numbering.longValue())
                .mapToObj(i -> new Diary(i, storage.get(i)))
                .collect(Collectors.toList());
    }

}
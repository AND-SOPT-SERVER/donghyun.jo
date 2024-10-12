package org.sopt.semina1;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

public class DiaryRepository implements Serializable {

    private Map<Long, String> storage = new ConcurrentHashMap<>();
    private Map<Long, String> trashCan = new ConcurrentHashMap<>();
    private AtomicLong numbering = new AtomicLong();
    private final String diaryPath = "diaries.post";
    private final String trashPath = "trash.post";

    public DiaryRepository() {
        loadDiaries();
        loadBin();

        Set<Long> allKeys = new HashSet<>();
        allKeys.addAll(storage.keySet());
        allKeys.addAll(trashCan.keySet());

        // 중복을 피하기 위해서 가장 큰 id 찾기
        if (!allKeys.isEmpty()) {
            Long maxId = Collections.max(allKeys);
            this.numbering = new AtomicLong(maxId);
        }
    }

    void save(final Diary diary){
        final Long id = numbering.addAndGet(1);
        storage.put(id, diary.getBody());
        saveDiaries();
    }

    void putOne(Long id, String body){
        storage.put(id, body);
        saveDiaries();
    }

    void deleteOne(Long id){
        String body = storage.get(id);
        storage.remove(id);
        trashCan.put(id, body);
        saveDiaries();
        saveBin();
    }

    String restore(Long id){
        String body = trashCan.get(id);
        if(body == null){
            throw new IllegalArgumentException("There is no such removed Diary");
        }
        trashCan.remove(id);
        putOne(id, body);
        saveDiaries();
        saveBin();
        return body;
    }

    List<Diary> findAll(){
        return LongStream.rangeClosed(1, numbering.longValue())
                .mapToObj(i -> new Diary(i, storage.get(i)))
                .collect(Collectors.toList());
    }

    private void saveDiaries() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(diaryPath))) {
            oos.writeObject(storage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadDiaries() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(diaryPath))) {
            storage = (Map<Long, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("create new post file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void saveBin() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(trashPath))) {
            oos.writeObject(trashCan);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadBin() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(trashPath))) {
            trashCan = (Map<Long, String>) ois.readObject();
        } catch (FileNotFoundException e) {
            System.out.println("create new post file");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
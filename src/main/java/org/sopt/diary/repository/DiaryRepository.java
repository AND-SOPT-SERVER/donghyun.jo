package org.sopt.diary.repository;

import org.sopt.diary.api.OrderBy;
import org.sopt.diary.service.Category;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    boolean existsByTitle(String title);

    List<DiaryEntity> findAll();

    DiaryEntity findByCategory(Category category);

    // 커서 기반 페이징
    @Query("SELECT d FROM DiaryEntity d WHERE (LENGTH(d.content) < :lastContentLength OR (LENGTH(d.content) = :lastContentLength AND d.createdAt > :lastCreatedAt)) ORDER BY LENGTH(d.content) DESC, d.createdAt ASC")
    List<DiaryEntity> findNextDiariesByCursor(int lastContentLength, LocalDateTime lastCreatedAt, Pageable pageable);


    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.content) DESC, d.createdAt DESC")
    List<DiaryEntity> findAllOrderByBodyLengthDesc();

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.content) DESC, d.createdAt ASC")
    List<DiaryEntity> findAllOrderByBodyLengthAsc();

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY LENGTH(d.content) DESC, d.createdAt DESC")
    List<DiaryEntity> findByCategoryOrderByBodyLengthDesc(Category category);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY LENGTH(d.content) ASC, d.createdAt DESC")
    List<DiaryEntity> findByCategoryOrderByBodyLengthAsc(Category category);


}

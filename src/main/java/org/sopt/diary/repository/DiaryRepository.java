package org.sopt.diary.repository;

import org.sopt.diary.service.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DiaryRepository extends JpaRepository<DiaryEntity, Long> {

    boolean existsByTitle(String title);

    List<DiaryEntity> findAll();

    DiaryEntity findByCategory(Category category);

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.content) DESC, d.createdAt DESC")
    List<DiaryEntity> findAllOrderByBodyLengthDesc();

    @Query("SELECT d FROM DiaryEntity d ORDER BY LENGTH(d.content) DESC, d.createdAt ASC")
    List<DiaryEntity> findAllOrderByBodyLengthAsc();

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY LENGTH(d.content) DESC, d.createdAt DESC")
    List<DiaryEntity> findByCategoryOrderByBodyLengthDesc(Category category);

    @Query("SELECT d FROM DiaryEntity d WHERE d.category = :category ORDER BY LENGTH(d.content) ASC, d.createdAt DESC")
    List<DiaryEntity> findByCategoryOrderByBodyLengthAsc(Category category);

}

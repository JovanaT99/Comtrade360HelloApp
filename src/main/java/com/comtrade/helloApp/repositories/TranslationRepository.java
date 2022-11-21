package com.comtrade.helloApp.repositories;

import com.comtrade.helloApp.models.Translation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.LinkedList;

@Repository
public interface TranslationRepository extends JpaRepository<Translation,Long> {

    LinkedList<Translation> findAll();

    @Query("SELECT s From Translation s Where s.language=?1")
    Translation findByLanguage(String language);

    @Modifying
    @Query("UPDATE Translation SET translation = ?2 WHERE language = ?1")
    void updateLanguage(String language, String translation);

    @Query("DELETE FROM Translation WHERE language = :language")
    void delete(String language);
}

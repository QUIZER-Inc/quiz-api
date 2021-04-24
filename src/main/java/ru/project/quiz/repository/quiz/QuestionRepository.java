package ru.project.quiz.repository.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.project.quiz.dao.QuestionRepositoryCustom;
import ru.project.quiz.domain.entity.quiz.Question;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionRepository extends JpaRepository<Question,Long>, QuestionRepositoryCustom {
    @Override
    Optional<Question> findById(Long id);
    @Query(value = "SELECT * FROM Questions ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> getListOfRandomQuestions(@Param("limit") int limit);
    @Query(value = "SELECT * FROM Questions WHERE category_type IN (:category) ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> getListQuestionsBySampleName(@Param("limit") int limit,
                                                @Param("category") List<String> category);
    List<Question> getQuestionsByCategoryName(String categoryName);

}

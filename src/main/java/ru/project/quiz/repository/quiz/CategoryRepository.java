package ru.project.quiz.repository.quiz;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.project.quiz.domain.entity.quiz.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    void deleteByName(String name);
    Category findByName(String id);

}

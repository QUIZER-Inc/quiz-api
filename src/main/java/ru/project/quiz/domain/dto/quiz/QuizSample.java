package ru.project.quiz.domain.dto.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.quiz.domain.enums.question.CategoryType;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class QuizSample {

    long id;

    String name;

    List<CategoryType> subCategories;
}

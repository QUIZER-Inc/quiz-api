package ru.project.quiz.mapper.quiz;

import org.mapstruct.Mapper;
import ru.project.quiz.domain.dto.quiz.CategoryDTO;
import ru.project.quiz.domain.dto.quiz.QuestionDTO;
import ru.project.quiz.domain.entity.quiz.Category;
import ru.project.quiz.domain.entity.quiz.Question;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question questionFromQuestionDTO(QuestionDTO questionDTO);

    QuestionDTO questionDTOFromQuestion(Question question);

    List<Question> listQuestionFromQuestionDTO(List<QuestionDTO> questionDTOS);

    List<QuestionDTO> listQuestionDTOFromQuestion(List<Question> questions);

}

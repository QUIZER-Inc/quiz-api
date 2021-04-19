package ru.project.quiz.domain.entity.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.project.quiz.domain.entity.BaseEntity;
import ru.project.quiz.domain.enums.question.CategoryType;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@Data
@Table(name = "quiz_sample")
public class QuizSample extends BaseEntity {

    @OneToMany(mappedBy = "quizSample")
    private List<Quiz> quizes;

    @Column(name = "name", unique = true)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "quiz_sample_sub_category",
            joinColumns = @JoinColumn(name = "quiz_sample_id")
    )
    @Column(name = "sub_category")
    @Enumerated(EnumType.STRING)
    private List<CategoryType> subCategories;

    public QuizSample() {
    }
}

package ru.project.quiz.domain.entity.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.project.quiz.domain.entity.ituser.ITUser;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@Data
@NoArgsConstructor
public class Rating {

    @Id
    private long id;

    @ManyToOne
    @JoinColumn(name = "ituser_id")
    private ITUser user;

    @Column(name = "passed_quiz")
    private int passedQuiz;

    @Column(name = "right_answers")
    private int rightAnswers;
}

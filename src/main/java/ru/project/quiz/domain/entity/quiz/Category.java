package ru.project.quiz.domain.entity.quiz;

import ru.project.quiz.domain.enums.question.CategoryType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    @NotEmpty
    private String categoryName;

    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    @NotNull
    private CategoryType category;

    @ManyToOne
    private QuizSample quizSample;

    public Category(CategoryType category, QuizSample quizSample) {
        this.category = category;
        this.quizSample = quizSample;
    }

    public Category() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category1 = (Category) o;
        return category == category1.category && Objects.equals(quizSample, category1.quizSample);
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, quizSample);
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryType getCategory() {
        return category;
    }

    public void setCategory(CategoryType category) {
        this.category = category;
    }

    public QuizSample getQuizSample() {
        return quizSample;
    }

    public void setQuizSample(QuizSample quizSample) {
        this.quizSample = quizSample;
    }
}

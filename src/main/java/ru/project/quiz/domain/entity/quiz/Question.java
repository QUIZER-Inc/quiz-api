package ru.project.quiz.domain.entity.quiz;

import ru.project.quiz.domain.entity.BaseEntity;
import ru.project.quiz.domain.enums.question.DifficultyType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "questions")
public class Question extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "difficulty_type")
    @Enumerated(EnumType.STRING)
    private DifficultyType difficultyType;

    @OneToOne
    private Category category;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;

    public Question(String name, String description, String imageUrl, DifficultyType difficultyType, Category category, List<Answer> answers) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.difficultyType = difficultyType;
        this.category = category;
        this.answers = answers;
    }

    public Question() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(name, question.name) && Objects.equals(description, question.description) && Objects.equals(imageUrl, question.imageUrl) && difficultyType == question.difficultyType && category == question.category && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, imageUrl, difficultyType, category, answers);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", difficultyType=" + difficultyType +
                ", category=" + category +
                ", answers=" + answers +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public DifficultyType getDifficultyType() {
        return difficultyType;
    }

    public void setDifficultyType(DifficultyType difficultyType) {
        this.difficultyType = difficultyType;
    }

    public Category getCategoryType() {
        return category;
    }

    public void setCategoryType(Category category) {
        this.category = category;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}

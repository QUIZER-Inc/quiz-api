package ru.project.quiz.domain.entity.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.project.quiz.domain.entity.BaseEntity;
import ru.project.quiz.domain.enums.ituser.PermissionType;
import ru.project.quiz.domain.enums.question.CategoryType;
import ru.project.quiz.domain.enums.question.DifficultyType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
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

    @Column(name = "category_name")
    private String categoryName;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "question_sub_category",
            joinColumns = @JoinColumn(name = "question_id")
    )
    @Column(name = "sub_category")
    @Enumerated(EnumType.STRING)
    private Set<CategoryType> subCategories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "question_id")
    private List<Answer> answers;

    public Question() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return Objects.equals(name, question.name) && Objects.equals(description, question.description) && Objects.equals(imageUrl, question.imageUrl) && difficultyType == question.difficultyType && Objects.equals(categoryName, question.categoryName) && Objects.equals(subCategories, question.subCategories) && Objects.equals(answers, question.answers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, imageUrl, difficultyType, categoryName, subCategories, answers);
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
                ", updatedAt=" + updatedAt +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", difficultyType=" + difficultyType +
                ", categoryName='" + categoryName + '\'' +
                ", subCategories=" + subCategories +
                ", answers=" + answers +
                '}';
    }

}

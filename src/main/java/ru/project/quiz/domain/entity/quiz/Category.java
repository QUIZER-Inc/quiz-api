package ru.project.quiz.domain.entity.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.project.quiz.domain.entity.BaseEntity;
import ru.project.quiz.domain.enums.question.CategoryType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Table(name = "categories")
public class Category extends BaseEntity {

    @Column(name = "name")
    @NotBlank
    private String name;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "category_sub_category",
            joinColumns = @JoinColumn(name = "category_id")
    )
    @Column(name = "sub_category")
    @Enumerated(EnumType.STRING)
    private List<CategoryType> subCategories;
}

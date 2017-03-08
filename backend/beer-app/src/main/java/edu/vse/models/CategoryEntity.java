package edu.vse.models;

import static javax.persistence.GenerationType.AUTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import edu.vse.dtos.Category;

@Entity
@Table(name = "category")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = AUTO)
    private Integer id;

    private String name;

    @ManyToOne
    @JoinColumn(name="main_category")
    private CategoryEntity mainCategory;

    public CategoryEntity() {
    }

    public CategoryEntity(String name, CategoryEntity mainCategory) {
        this.name = name;
        this.mainCategory = mainCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryEntity getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(CategoryEntity mainCategory) {
        this.mainCategory = mainCategory;
    }

    public Category toDto() {
        return new Category(id, name, mainCategory == null ? null : mainCategory.getId());
    }
}

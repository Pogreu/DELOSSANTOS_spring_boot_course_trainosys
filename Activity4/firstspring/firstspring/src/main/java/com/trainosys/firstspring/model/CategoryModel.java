package com.trainosys.firstspring.model;

public class CategoryModel {

    private Long categoryId;
    private String categoryName;

    public CategoryModel(Long categoryId) {
        this.categoryId = categoryId;
    }

    public CategoryModel(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public CategoryModel() {
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}

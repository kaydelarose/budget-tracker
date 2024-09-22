package com.niantic.models;

public class Category {

    private int categoryId;
    private String categoryName;
    private String description;

    public Category(){

    }

    public Category(int categoryId, String categoryName, String description) {
        this.description = description;
        this.categoryName = categoryName;
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

//    public String toString() {
//        return String.format("%2d: Category Name - %s, Description - %s\n", categoryId, categoryName, description);
//    }


}


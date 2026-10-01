package com.trainosys.firstspring.service;

import com.trainosys.firstspring.model.CategoryModel;
import java.util.List;

public interface CategoryService {
    List<CategoryModel> getAllCategories();
    void createCategory(CategoryModel category);
}

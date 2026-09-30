package com.trainosys.firstspring.quick_challenge; // Make sure it's in the service package

import com.trainosys.firstspring.model.CategoryModel;
import com.trainosys.firstspring.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final List<CategoryModel> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<CategoryModel> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(CategoryModel category) {
        category.setCategoryId(nextId);
        categories.add(category);
        nextId++;
    }
}

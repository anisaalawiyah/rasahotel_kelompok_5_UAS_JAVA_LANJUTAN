package com.javaproject.rasahotel.services.category;

import java.util.List;

import com.javaproject.rasahotel.dto.category.CategoryRequestDto;
import com.javaproject.rasahotel.entities.Category;

public interface CategoryService {

    List<Category> getAll();

    Category get(String idCategory);

    Category add(CategoryRequestDto dto);

    Category update(String idCategory, CategoryRequestDto dto);

    void delete(String idCategory);
}

package com.javaproject.rasahotel.services.category;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.dto.category.CategoryRequestDto;
import com.javaproject.rasahotel.entities.Category;
import com.javaproject.rasahotel.entities.Sale;
import com.javaproject.rasahotel.repositories.CategoryRepository;
import com.javaproject.rasahotel.repositories.SaleRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    SaleRepository saleRepository;

    @Override
    public List<Category> getAll() {

        try {
            return categoryRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Category get(String idCategory) {

        try {

            Category category = categoryRepository.findById(idCategory).orElse(null);
            if (category == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
            return category;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Category add(CategoryRequestDto dto) {

        try {

            Category newCategory = new Category();
            Sale sale = saleRepository.findById(dto.getSaleId()).orElse(null);
            newCategory.setName(dto.getName());
            newCategory.setDesc(dto.getDesc());
            newCategory.setSaleId(sale);
            return categoryRepository.save(newCategory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Category update(String idCategory, CategoryRequestDto dto) {
        try {

            Category oldCategory = categoryRepository.findById(idCategory).orElse(null);
            Sale sale = saleRepository.findById(dto.getSaleId()).orElse(null);
            if (oldCategory == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
            if (!dto.getSaleId().isEmpty() || !dto.getSaleId().isBlank())
                if (sale == null)
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sale not found");
            oldCategory.setName(dto.getName());
            oldCategory.setDesc(dto.getDesc());
            oldCategory.setSaleId(sale);

            return categoryRepository.save(oldCategory);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    e.getMessage());
        }
    }

    @Override
    public void delete(String idCategory) {
        try {
            Category category = categoryRepository.findById(idCategory).orElse(null);
            if (category == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found");
            categoryRepository.delete(category);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

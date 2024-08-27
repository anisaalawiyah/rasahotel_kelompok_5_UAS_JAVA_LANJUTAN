package com.javaproject.rasahotel.controllers.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.constants.MessageConstant;
import com.javaproject.rasahotel.dto.GeneralResponse;
import com.javaproject.rasahotel.dto.category.CategoryRequestDto;
import com.javaproject.rasahotel.services.category.CategoryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/api/category")
@Tag(name = "Category")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all-category")
    ResponseEntity<Object> getCategories() {
        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(categoryService.getAll(), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get-category")
    ResponseEntity<Object> getCategory(@RequestParam String idCategory) {
        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(categoryService.get(idCategory), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/add-category")
    ResponseEntity<Object> addCategory(@RequestBody CategoryRequestDto dto) {

        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(categoryService.add(dto), MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update-category")
    ResponseEntity<Object> updateCategory(@RequestParam String idCategory, @RequestBody CategoryRequestDto dto) {

        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(categoryService.update(idCategory, dto),
                            MessageConstant.OK_PUT_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete-category")
    ResponseEntity<Object> deleteCategory(@RequestParam String idCategory) {

        try {
            categoryService.delete(idCategory);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(null, MessageConstant.OK_DELETE_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getMessage());
            return ResponseEntity.status(e.getStatusCode()).body(GeneralResponse.error(e.getReason()));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

}

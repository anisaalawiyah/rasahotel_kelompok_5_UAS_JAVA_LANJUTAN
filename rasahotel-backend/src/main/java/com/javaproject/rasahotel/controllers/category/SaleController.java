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
import com.javaproject.rasahotel.dto.category.SaleRequestDto;
import com.javaproject.rasahotel.services.category.SaleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@RestController
@Slf4j
@RequestMapping("/api/category")
@Tag(name = "Sales")
public class SaleController {

    @Autowired
    SaleService saleService;

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/all-sale")
    ResponseEntity<Object> getAllSales() {

        try {

            return ResponseEntity.ok().body(GeneralResponse.success(saleService.getAll(), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.internalServerError().body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get-sale")
    ResponseEntity<Object> getSale(@RequestParam String idSale) {

        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(saleService.get(idSale), MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.internalServerError().body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/add-sale")
    ResponseEntity<Object> addSale(@RequestBody SaleRequestDto dto) {

        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(saleService.set(dto), MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.internalServerError().body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @PutMapping("/update-sale")
    ResponseEntity<Object> updateSale(@RequestParam String idSale, @RequestBody SaleRequestDto dto) {

        try {

            return ResponseEntity.ok()
                    .body(GeneralResponse.success(saleService.update(idSale, dto), MessageConstant.OK_PUT_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.internalServerError().body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @DeleteMapping("/delete-sale")
    ResponseEntity<Object> deleteSale(@RequestParam String idSale) {

        try {

            saleService.delete(idSale);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(null, MessageConstant.OK_DELETE_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.internalServerError().body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }
}

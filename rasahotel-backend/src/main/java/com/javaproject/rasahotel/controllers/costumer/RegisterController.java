package com.javaproject.rasahotel.controllers.costumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.constants.MessageConstant;
import com.javaproject.rasahotel.dto.GeneralResponse;
import com.javaproject.rasahotel.dto.costumer.CostumerRequestDto;
import com.javaproject.rasahotel.entities.Customer;
import com.javaproject.rasahotel.services.costumer.CustomerService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin("*")
@Slf4j
@RestController
@RequestMapping("/api/customer")
@Tag(name = "Costumer")
public class RegisterController {

    @Autowired
    CustomerService costumerService;

    @SecurityRequirement(name = "Bearer Authentication")
    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody CostumerRequestDto dto) {
        try {
            Customer response = costumerService.register(dto);
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(response,
                            MessageConstant.OK_POST_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/get-user")
    public ResponseEntity<Object> getUser(@RequestParam String email) {
        try {
            
            return ResponseEntity.ok()
                    .body(GeneralResponse.success(costumerService.getCustomerByEmail(email),
                            MessageConstant.OK_GET_DATA));
        } catch (ResponseStatusException e) {
            log.info(e.getReason());
            return ResponseEntity.status(e.getStatusCode())
                    .body(GeneralResponse.error(MessageConstant.BAD_REQUEST));
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(GeneralResponse.error(MessageConstant.INTERNAL_SERVER_ERROR));
        }
    }
}

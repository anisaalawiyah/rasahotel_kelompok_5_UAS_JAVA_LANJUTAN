package com.javaproject.rasahotel.services.costumer;

import com.javaproject.rasahotel.dto.costumer.CostumerRequestDto;
import com.javaproject.rasahotel.entities.Customer;
import com.javaproject.rasahotel.entities.Users;

public interface CustomerService {
    Customer register(CostumerRequestDto dto);

    void deleteUser(String email);

    Users getByEmail(String email);

    Customer getCustomerByEmail(String email);
    
}

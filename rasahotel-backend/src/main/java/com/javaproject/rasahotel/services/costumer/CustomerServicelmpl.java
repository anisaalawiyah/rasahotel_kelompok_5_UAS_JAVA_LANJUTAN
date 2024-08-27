package com.javaproject.rasahotel.services.costumer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.javaproject.rasahotel.constants.RoleConstant;
import com.javaproject.rasahotel.dto.costumer.CostumerRequestDto;
import com.javaproject.rasahotel.entities.Customer;
import com.javaproject.rasahotel.entities.Users;
import com.javaproject.rasahotel.repositories.CustomerRepository;
import com.javaproject.rasahotel.repositories.RolesRepository;
import com.javaproject.rasahotel.repositories.UsersRepository;
import com.javaproject.rasahotel.services.EmailService;

import jakarta.transaction.Transactional;

@Service
public class CustomerServicelmpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    RolesRepository rolesRepository;
    @Autowired
    EmailService emailService;

    @Override
    @Transactional
    public Customer register(CostumerRequestDto dto) {
        try {

            List<Customer> customers = customerRepository.findAll().stream()
                    .filter(data -> data.getEmail().equalsIgnoreCase(dto.getEmail())).map(n -> n).toList();

            if (customers == null)
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email was registered");

            Customer customer = new Customer();
            customer.setName(dto.getName());
            customer.setEmail(dto.getEmail());
            customer.setAddress(dto.getAddress());
            customer.setPhoneNumber(dto.getPhoneNumber());

            Users user = new Users();
            user.setUsername(dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
            user.setRoles(rolesRepository.findByRoleName(RoleConstant.USER_ROLE));
            sendEmail(dto.getEmail());
            usersRepository.save(user);

            return customerRepository.save(customer);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private void sendEmail(String to) {
        String subject = "Customer Registration";
        String text = "Selamat anda berhasil mendaftar";

        emailService.sendMailMessage(to, subject, text);
    }

    @Override
    public void deleteUser(String email) {
        customerRepository.deleteByEmail(email);
    }

    @Override
    public Users getByEmail(String email) {

        try {

            return usersRepository.findByUsername(email).orElse(null);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public Customer getCustomerByEmail(String email) {

        try {
            Customer customer = customerRepository.findByEmail(email).orElse(null);
            if (customer != null)
                return customer;
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
        } catch (Exception e) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

}

package com.javaproject.rasahotel.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.javaproject.rasahotel.constants.RoleConstant;
import com.javaproject.rasahotel.entities.Roles;
import com.javaproject.rasahotel.entities.Users;
import com.javaproject.rasahotel.repositories.RolesRepository;
import com.javaproject.rasahotel.repositories.UsersRepository;

@Component //komponen spring yang general
public class InitialDataLoader implements ApplicationRunner {
    @Autowired
    RolesRepository rolesRepository;

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<Roles> roles = rolesRepository.findAll();

        if (roles.isEmpty()) {

            //KARENA DAH PAKE CONSTANT
            Roles admin = new Roles(null, RoleConstant.ADMIN_ROLE, "Role as Admin in Application");
            Roles user = new Roles(null, RoleConstant.USER_ROLE, "Role as User in Application");

            rolesRepository.saveAll(List.of(admin, user));

            // Initial First Admin Account
            Users userAdmin = new Users();
            userAdmin.setUsername("admin@rasahotel.com");
            userAdmin.setPassword(passwordEncoder.encode("123"));
            userAdmin.setRoles(admin);

            usersRepository.save(userAdmin);
        }

    }

}
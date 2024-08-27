package com.javaproject.rasahotel.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.javaproject.rasahotel.entities.Users;

public interface UsersRepository extends JpaRepository<Users, String> {

        Optional<Users> findByUsername(String email);

        @Query("delete from Users where username=:email")
        void deleteByEmail(String email);
}

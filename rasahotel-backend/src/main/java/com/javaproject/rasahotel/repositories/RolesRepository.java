package com.javaproject.rasahotel.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javaproject.rasahotel.entities.Roles;

public interface RolesRepository extends JpaRepository<Roles, String> {
    Roles findByRoleName(String roleName);
}

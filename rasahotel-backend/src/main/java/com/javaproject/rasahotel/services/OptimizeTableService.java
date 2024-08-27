package com.javaproject.rasahotel.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;

@Service
public class OptimizeTableService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EntityManager entityManager;

    public void optimizeTable(String tableName) {
        String optimizeQuery = "OPTIMIZE TABLE " + tableName;
        jdbcTemplate.execute(optimizeQuery);
    }

    public String getTableName(Class<?> entityClass) {
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<?> entityType = metamodel.entity(entityClass);
        return entityType.getName();
    }
}

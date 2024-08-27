package com.javaproject.rasahotel.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javaproject.rasahotel.dto.PageResponse;
import com.javaproject.rasahotel.entities.Room;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class RoomDaolpml implements RoomDao {

    @Autowired
    EntityManager entityManager;

    @Override
    public PageResponse<Room> findAll(String name, Long price, int page, int size, String sort) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        //total per page
        CriteriaQuery<Room> criteriaQuery = criteriaBuilder.createQuery(Room.class);
        Root<Room> roomRoot = criteriaQuery.from(Room.class);

        if (sort != null) {

            if (sort.equalsIgnoreCase("asc"))
                criteriaQuery.where(createPredicate(criteriaBuilder, roomRoot, name, price))
                        .orderBy(criteriaBuilder.asc(roomRoot.get("name")));

            if (sort.equalsIgnoreCase("desc"))
                criteriaQuery.where(createPredicate(criteriaBuilder, roomRoot, name, price))
                        .orderBy(criteriaBuilder.desc(roomRoot.get("name")));

        } else
            criteriaQuery.where(createPredicate(criteriaBuilder, roomRoot, name, price));

        List<Room> result = entityManager.createQuery(criteriaQuery)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();

        // total item
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        Root<Room> roomrootCount = countQuery.from(Room.class);
        countQuery.select(criteriaBuilder.count(roomrootCount))
                .where(createPredicate(criteriaBuilder, roomrootCount, name, price));
        Long totalItem = entityManager.createQuery(countQuery).getSingleResult();

        return PageResponse.success(result, page, size, totalItem);
    }

    private Predicate[] createPredicate(CriteriaBuilder criteriaBuilder, Root<Room> root, String name, Long price) {
        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isBlank() && !name.isEmpty()) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
        }
        if (price != null) {
            predicates.add(criteriaBuilder.equal(root.get("price"), price));
        }

        return predicates.toArray(new Predicate[0]);
    }
}
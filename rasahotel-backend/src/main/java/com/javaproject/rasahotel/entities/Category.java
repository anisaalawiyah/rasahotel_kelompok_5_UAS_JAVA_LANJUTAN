package com.javaproject.rasahotel.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "category_name")
    private String name;

    @Column(name = "category_description", columnDefinition = "text")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "sale_id", referencedColumnName = "id", insertable = true, unique = false)
    private Sale saleId;
}

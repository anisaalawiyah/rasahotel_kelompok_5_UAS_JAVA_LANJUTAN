package com.javaproject.rasahotel.entities;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "room_name")
    private String name;

    @Column(name = "room_description",length = 1000)
    private String desc;

    @Column(name = "room_price")
    private Long price;

    @Lob
    @Column(name = "photo")
    @JsonIgnore
    private Blob photo;

    @JsonProperty("photo")
    public String getPhotoBase64() throws SQLException {
        if (photo != null)
            return new String(Base64.getEncoder().encode(photo.getBytes(1L, (int) photo.length())));
        return null;
    }

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id", insertable = true, unique = false)
    private Category category;

}

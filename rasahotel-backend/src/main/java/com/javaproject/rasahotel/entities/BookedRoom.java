package com.javaproject.rasahotel.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookedRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id",  nullable = false)
    private String bookingId;

    @Column(name = "check_in")
    private LocalDate checkInDate;

    @Column(name = "check_out")
    private LocalDate checkOutDate;

    @Column(name = "confirmation_Code")
    private String bookingConfirmationCode;

    @ManyToOne
    @JoinColumn(name = "room_id", referencedColumnName = "id", insertable = true, unique = false)
    private Room room;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = true, unique = false)
    private Users users;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = true, unique = false)
    private Customer customer;

    private boolean paymentRequired = false;

    @JoinColumn(name = "total_payment")
    private Long totalPayment;
}
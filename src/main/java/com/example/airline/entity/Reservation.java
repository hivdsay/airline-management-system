package com.example.airline.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*Bir kullanıcı, bir veya birden fazla uçuş rezervasyonu yapabilir.
Bir uçuş, birçok kişiye ait rezervasyonlar içerir.
Bir rezervasyon, sadece 1 user + 1 flight ilişkisi taşır.*/

@Entity
@Table(name = "reservations")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) //LAZY → ilişkiyi sadece ihtiyaç olursa çağırır
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flight_id", nullable = false)
    private Flight flight;

    @Column(nullable = false)
    private int seatNumber;

    @Column(nullable = false)
    private String status;
}

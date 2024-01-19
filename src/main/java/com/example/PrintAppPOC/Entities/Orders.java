package com.example.PrintAppPOC.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Files> fileNames;
    private BigInteger orderAmount;
    private String paymentId;
    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
    @ManyToOne
    private Users user;
    private LocalDateTime localDateTime;

}

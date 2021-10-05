package com.sec.backend.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "credit_card")
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Type(type = "long")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "card_name", nullable = false)
    private String cardName;

    @Column(name = "card_number", nullable = false)
    private String cardNumber;

    @Column(name = "exp_date", nullable = false)
    private String expDate;

    @Column(nullable = false)
    private String cvv;

}

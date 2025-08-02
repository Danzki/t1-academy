package com.danzki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProduct {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator="seq_id")
    @SequenceGenerator(name = "user_product_seq", sequenceName = "seq_id", allocationSize=1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "account_num")
    private String accountNum;

    @Column(name = "balance")
    private Long balance;

    @Column(name = "product_type")
    @Enumerated(EnumType.STRING)
    private ProductType productType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

}

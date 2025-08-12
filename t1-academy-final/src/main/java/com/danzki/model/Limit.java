package com.danzki.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "limits")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Limit {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator="seq_id")
    @SequenceGenerator(name = "limit_seq", sequenceName = "seq_id", allocationSize=1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @Column(name = "current_amount", nullable = false)
    private Double currentValue;

}

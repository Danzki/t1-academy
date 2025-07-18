package com.danzki.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "user", schema = "ms_store_schema")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE,
            generator="seq_id")
    @SequenceGenerator(name = "user_seq", sequenceName = "seq_id", allocationSize=1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "username")
    private String username;
}

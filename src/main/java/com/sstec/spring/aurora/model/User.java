package com.sstec.spring.aurora.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@ToString(onlyExplicitlyIncluded = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ToString.Include
    private long id;

    @Column(nullable = false, name = "name")
    @ToString.Include
    String name;

    @Column(nullable = false, name="email")
    @ToString.Include
    String email;

    @Column(name = "creation_date", columnDefinition = "TIMESTAMP")
    @ToString.Include
    private LocalDateTime creationDate;

}
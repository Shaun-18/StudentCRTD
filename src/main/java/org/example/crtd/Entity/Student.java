package org.example.crtd.Entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
    @Setter
    @Entity
    @Data
    @AllArgsConstructor
    @NoArgsConstructor


    @Table(name = "Students")
    public class Student {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @Column(name = "firstname", nullable = false)
        private String firstname;

        @Column(name = "lastname", nullable = false)
        private String lastname;

        @Column(name = "email", nullable = false, unique = true)
        private String email;

    }


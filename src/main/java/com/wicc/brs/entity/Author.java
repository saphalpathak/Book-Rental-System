package com.wicc.brs.entity;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name="brs_author",
        uniqueConstraints = {
                @UniqueConstraint(name="unique_author_email",columnNames = "email"),
                @UniqueConstraint(name="unique_author_contact",columnNames = "contact")
        }
)
public class Author {
    @Id
    @SequenceGenerator(name = "author_id_GEN",sequenceName = "author_id_GEN",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "author_id_GEN")
    private Integer id;

    @Column(name="name",nullable = false)
    private String name;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name = "contact",nullable = false)
    private String contact;
}

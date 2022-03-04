package com.wicc.brs.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name="brs_member",
        uniqueConstraints = {
                @UniqueConstraint(name="unique_member_email",columnNames = "email"),
                @UniqueConstraint(name="unique_member_contact",columnNames = "contact")
        }
)
public class Member {
    @Id
    @SequenceGenerator(name="member_id_gen" , sequenceName ="member_id_gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "member_id_gen")
    private Integer mid;

    @Column(nullable = false,length = 50)
    private String name;

    @Column(name="email",nullable = false)
    private String email;

    @Column(name="contact",nullable = false)
    private String contact;

    @Column(name="address",nullable = false,length = 100)
    private String address;


}

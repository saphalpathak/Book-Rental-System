package com.wicc.brs.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(
        name="brs_category"
)
public class Category {
    @Id
    @SequenceGenerator(name="category_id_GEN",sequenceName = "category_id_GEN" )
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_id_GEN")
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT",nullable = false)
    private String description;
}

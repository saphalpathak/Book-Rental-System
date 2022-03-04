package com.wicc.brs.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Book {
    @Id
    @SequenceGenerator(name = "book_id_gen", sequenceName = "book_id_gen", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_id_gen")
    private Integer id;

    @Column(length = 100,nullable = false)
    private String name;

    @Column(name="no_of_page",nullable = false)
    private Integer noOfPage;

    @Column(nullable = false,unique = true)
    private Integer isbn;
    @Column(nullable = false)
    private Double rating;

    @Column(name="stock_count",nullable = false)
    private Integer stockCount;

    @Column(name="published_date",nullable = false)
    private Date publishedDate;

    @Column(length = 200)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "category_id",referencedColumnName = "id" ,foreignKey = @ForeignKey(name = "FK_Book_Category"))
    private Category category;

    @ManyToMany
    @JoinTable(name = "book_author",
            joinColumns = @JoinColumn(name = "bid"),
            inverseJoinColumns = @JoinColumn(name = "aid")
    )
    private List<Author> authors;
}

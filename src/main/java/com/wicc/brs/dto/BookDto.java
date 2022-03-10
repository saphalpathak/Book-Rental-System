package com.wicc.brs.dto;

import com.wicc.brs.entity.Author;
import com.wicc.brs.entity.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {
    private Integer id;

    private String name;

    private Integer noOfPage;

    private Integer isbn;

    private Double rating;

    private Integer totalStock;

    private String publishedDate;

    private MultipartFile multipartFile;

    private String filePath;

    private Category category;

    private Integer remainingBook;

    private List<Author> authors;

}

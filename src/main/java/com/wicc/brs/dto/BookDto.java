package com.wicc.brs.dto;

import com.wicc.brs.entity.Author;
import com.wicc.brs.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private Integer id;

    private String name;

    private Integer noOfPage;

    private Integer isbn;

    private Double rating;

    private Integer stockCount;

    private String publishedDate;

    private MultipartFile multipartFile;

    private String filePath;

    private Category category;

    private List<Author> authors;

}

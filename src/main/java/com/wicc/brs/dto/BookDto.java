package com.wicc.brs.dto;

import com.wicc.brs.entity.Author;
import com.wicc.brs.entity.Category;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BookDto {
    private Integer id;

    @NotEmpty(message = "Book name can not be Empty")
    private String name;

    @NotNull(message = "Number of page can not br Empty")
    private Integer noOfPage;

    @NotNull(message = "Isbn of page can not br Empty")
    private Integer isbn;

    @NotNull(message = "Rating of page can not br Empty")
    private Double rating;

    @NotNull(message = "Stock of page can not br Empty")
    private Integer totalStock;

    @NotEmpty(message = "Published Date can not be Empty")
    private String publishedDate;

    private MultipartFile multipartFile;

    private String filePath;


    private Category category;

    private Integer remainingBook;

    private List<Author> authors;

}

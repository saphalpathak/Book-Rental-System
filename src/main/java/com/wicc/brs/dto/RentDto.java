package com.wicc.brs.dto;

import com.wicc.brs.entity.Book;
import com.wicc.brs.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDto {

    private Integer id;

    @NotEmpty(message = "Code name can not be Empty")
    private String code;

    private Book book;

    private Member member;

    @NotNull(message = "No of days name can not be Empty")
    private Integer noOfDays;
}

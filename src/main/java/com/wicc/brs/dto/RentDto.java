package com.wicc.brs.dto;

import com.wicc.brs.entity.Book;
import com.wicc.brs.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RentDto {

    private Integer id;

    private String code;

    private Book book;

    private Member member;

    private Integer noOfDays;
}

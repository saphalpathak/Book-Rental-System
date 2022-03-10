package com.wicc.brs.dto;


import com.wicc.brs.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReturnDto {

    private Integer id;

    private String code;

    private Date dateOfReturn;

    private Member member;
}
package com.wicc.brs.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Integer mid;

    private String name;

    private String email;

    private String contact;

    private String address;


}

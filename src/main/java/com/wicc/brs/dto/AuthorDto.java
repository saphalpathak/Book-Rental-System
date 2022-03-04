package com.wicc.brs.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthorDto {
    private Integer id;

    private String name;

    private String email;

    private String contact;
}

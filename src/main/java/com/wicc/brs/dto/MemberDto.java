package com.wicc.brs.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Integer mid;

    @NotEmpty(message = "Name can not be Empty")
    private String name;

    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$",message = "Invalid Email !! please type a valid email.")
    @NotEmpty(message = "Email must not be empty")
    private String email;

    @Pattern(regexp = "^\\+?(?:977)?[ -]?(?:(?:(?:98|97)-?\\d{8})|(?:01-?\\d{7}))$",
            message = "Wrong mobile number or number must be of 10 digit ")
    @NotEmpty(message = "Contact must not be empty")
    private String contact;

    @NotEmpty(message = "Address name can not be Empty")
    private String address;


}

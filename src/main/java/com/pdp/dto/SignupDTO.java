package com.pdp.dto;

import lombok.*;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  19:17
 **/
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDTO {
    private String name;
    private String username;
    private String password;
    private String phoneNumber;
}

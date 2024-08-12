package com.pdp.dto;

import lombok.*;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  19:16
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class    LoginDTO {
    private String username;
    private String password;
}

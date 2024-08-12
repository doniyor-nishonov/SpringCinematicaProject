package com.pdp.domains;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  19:43
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser extends BaseDomain {
    private String name;
    private String username;
    private String password;
    private String phoneNumber;
    private String role;
    private Integer imageId;
    private String status;
}

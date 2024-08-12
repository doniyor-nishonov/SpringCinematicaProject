package com.pdp.domains;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 03/August/2024  19:34
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class Movie extends BaseDomain {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Double rating;
    private String trailerUrl;
    private String language;
    private Integer imageId;
}

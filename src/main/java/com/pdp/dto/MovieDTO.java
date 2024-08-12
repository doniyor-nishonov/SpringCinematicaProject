package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @author Doniyor Nishonov
 * @since 06/August/2024  16:29
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDTO {
    private Integer movie_id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private Double rating;
    private String trailerUrl;
    private String language;
    //images
    private String originalName;
    private String extension;
}

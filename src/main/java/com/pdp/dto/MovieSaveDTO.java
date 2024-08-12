package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


/**
 * @author Doniyor Nishonov
 * @since 06/August/2024  18:34
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieSaveDTO {
    private String title;
    private String category;
    private String description;
    private String trailerUrl;
    private Double rating;
    private LocalDate releaseDate;
    private String language;
    private MultipartFile file;
}

package com.pdp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Doniyor Nishonov
 * @since 10/August/2024  09:25
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EventDTO {
    private String name;
    private Integer capacity;
    private String soundSystem;
    private MultipartFile file;
}

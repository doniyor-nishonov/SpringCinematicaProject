package com.pdp.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * @author Doniyor Nishonov
 * @since 05/August/2024  09:34
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class Image extends BaseDomain {
    private String name;
    private String generatedName;
    private String mimeType;
    private String extension;
}

package com.pdp.utils;

import com.pdp.domains.Image;
import com.pdp.service.ImageService;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 10/August/2024  09:45
 **/
@Component
public class ImageUtils {
    private final Path rootPath = Path.of("/Users/user/Desktop/PDP_ACADEMY/pdp/ultimate/g40/modul 8/Cinematica/src/main/resources/static/img");
    private final ImageService imageService;

    public ImageUtils(ImageService imageService) {
        this.imageService = imageService;
    }

    @SneakyThrows
    public Integer saveFile(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String mimeType = StringUtils.getFilenameExtension(originalFilename);
        String generatedName = UUID.randomUUID() + "." + mimeType;
        String extension = "/static/img/" + generatedName;
        Files.copy(file.getInputStream(), rootPath.resolve(generatedName), StandardCopyOption.REPLACE_EXISTING);
        Image image = Image.builder()
                .name(originalFilename)
                .generatedName(generatedName)
                .mimeType(mimeType)
                .extension(extension)
                .build();
        return imageService.save(image);
    }
}

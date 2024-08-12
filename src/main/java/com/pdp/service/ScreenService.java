package com.pdp.service;

import com.pdp.daos.ScreenDAO;
import com.pdp.domains.Image;
import com.pdp.domains.Screen;
import com.pdp.dto.EventDTO;
import com.pdp.dto.ScreenDTO;
import com.pdp.utils.ImageUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:59
 **/
@Component
public class ScreenService implements BaseService<Screen, Integer> {
    private final ScreenDAO dao;
    private final ImageService imageService;
    private final ImageUtils imageUtils;

    @Autowired
    public ScreenService(ScreenDAO dao, ImageService imageService, ImageUtils imageUtils) {
        this.dao = dao;
        this.imageService = imageService;
        this.imageUtils = imageUtils;
    }

    @Override
    public Integer save(Screen domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Screen domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Screen findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Screen> findAll() {
        return dao.findAll();
    }

    public List<ScreenDTO> findAllWithImages() {
        return dao.findAllWithImages();
    }

    public void saveWithDTO(EventDTO dto) {
        Integer imageId = imageUtils.saveFile(dto.getFile());
        Screen event = Screen.builder()
                .cinemaID(1)
                .imageId(imageId)
                .name(dto.getName())
                .seatingCapacity(dto.getCapacity())
                .soundSystem(dto.getSoundSystem())
                .build();
        save(event);
    }

}

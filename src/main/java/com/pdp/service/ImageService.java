package com.pdp.service;

import com.pdp.daos.ImageDAO;
import com.pdp.domains.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 05/August/2024  09:42
 **/
@Component
public class ImageService implements BaseService<Image, Integer> {
    private final ImageDAO dao;

    @Autowired
    public ImageService(ImageDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(Image domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Image domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Image findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Image> findAll() {
        return dao.findAll();
    }
}

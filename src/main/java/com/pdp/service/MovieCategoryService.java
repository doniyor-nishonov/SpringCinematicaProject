package com.pdp.service;

import com.pdp.daos.MovieCategoryDAO;
import com.pdp.domains.MovieCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 06/August/2024  14:19
 **/
@Component
public class MovieCategoryService implements BaseService<MovieCategory, Integer> {
    private final MovieCategoryDAO dao;

    @Autowired
    public MovieCategoryService(MovieCategoryDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(MovieCategory domain) {
        return dao.save(domain);
    }

    @Override
    public void update(MovieCategory domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public MovieCategory findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<MovieCategory> findAll() {
        return dao.findAll();
    }
}

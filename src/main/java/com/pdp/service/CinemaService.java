package com.pdp.service;

import com.pdp.daos.CinemaDAO;
import com.pdp.domains.Cinema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:58
 **/
@Component
public class CinemaService implements BaseService<Cinema, Integer> {
    private final CinemaDAO dao;

    @Autowired
    public CinemaService(CinemaDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(Cinema domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Cinema domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Cinema findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Cinema> findAll() {
        return dao.findAll();
    }
}

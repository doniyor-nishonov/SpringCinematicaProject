package com.pdp.service;

import com.pdp.daos.ReviewDAO;
import com.pdp.domains.Review;
import com.pdp.dto.ReviewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:58
 **/
@Component
public class ReviewService implements BaseService<Review, Integer> {

    private final ReviewDAO dao;

    @Autowired
    public ReviewService(ReviewDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(Review domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Review domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Review findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Review> findAll() {
        return dao.findAll();
    }

    public List<ReviewDTO> findAllByMovieId(Integer movieId) {
        return dao.findAllByMovieId(movieId);
    }
}

package com.pdp.service;

import com.pdp.daos.ShowtimeDAO;
import com.pdp.domains.Showtime;
import com.pdp.dto.ShowtimeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  11:59
 **/
@Component
public class ShowtimeService implements BaseService<Showtime, Integer> {
    private final ShowtimeDAO dao;

    @Autowired
    public ShowtimeService(ShowtimeDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(Showtime domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Showtime domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Showtime findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Showtime> findAll() {
        return dao.findAll();
    }

    public void saveDTO(ShowtimeDTO dto) {
        if (Objects.isNull(dto) || Objects.isNull(dto.getScreenId()) || Objects.isNull(dto.getMovieId()))
            return;
        Showtime showtime = Showtime.builder()
                .moveId(dto.getMovieId())
                .screenId(dto.getScreenId())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .language(dto.getLanguage())
                .price(dto.getPrice())
                .build();
        save(showtime);
    }

    public List<ShowtimeDTO> findByMovieId(Integer movieId) {
        return dao.findByMovieId(movieId);
    }
}

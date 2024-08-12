package com.pdp.service;

import com.pdp.daos.HallSeatDAO;
import com.pdp.domains.HallSeat;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Doniyor Nishonov
 * @since 08/August/2024  11:53
 **/
@Component
public class HallSeatService implements BaseService<HallSeat, Integer> {
    private final HallSeatDAO dao;

    public HallSeatService(HallSeatDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(HallSeat domain) {
        return dao.save(domain);
    }

    @Override
    public void update(HallSeat domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer integer) {
        dao.delete(integer);
    }

    @Override
    public HallSeat findById(Integer integer) {
        return dao.findById(integer);
    }

    @Override
    public List<HallSeat> findAll() {
        return dao.findAll();
    }

    public List<Integer> findSeatNumberByShowtimeId(Integer showtimeId) {
        return Objects.nonNull(showtimeId) ? dao.findSeatNumberByShowtimeId(showtimeId) : Collections.emptyList();
    }
}

package com.pdp.service;

import com.pdp.daos.TicketDAO;
import com.pdp.domains.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  12:00
 **/
@Component
public class TicketService implements BaseService<Ticket, Integer> {
    private final TicketDAO dao;

    @Autowired
    public TicketService(TicketDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(Ticket domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Ticket domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Ticket findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Ticket> findAll() {
        return dao.findAll();
    }
}

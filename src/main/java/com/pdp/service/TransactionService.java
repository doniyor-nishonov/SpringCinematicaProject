package com.pdp.service;

import com.pdp.daos.TransactionDAO;
import com.pdp.domains.Transaction;
import com.pdp.dto.TransactionDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 04/August/2024  12:00
 **/
@Component
public class TransactionService implements BaseService<Transaction, Integer> {
    private final TransactionDAO dao;

    @Autowired
    public TransactionService(TransactionDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(Transaction domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Transaction domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Transaction findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Transaction> findAll() {
        return dao.findAll();
    }

    public List<TransactionDTO> findAllByUserId(Integer id) {
        return dao.findAllByUserId(id);
    }
}

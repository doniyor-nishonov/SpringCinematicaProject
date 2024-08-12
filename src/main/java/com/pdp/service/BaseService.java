package com.pdp.service;

import com.pdp.domains.BaseDomain;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  20:10
 **/
@Component
public interface BaseService<T extends BaseDomain, ID extends Serializable> {
    ID save(T domain);

    void update(T domain);

    void delete(ID id);

    T findById(ID id);

    List<T> findAll();
}

package com.pdp.service;

import com.pdp.daos.CategoryDAO;
import com.pdp.domains.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Doniyor Nishonov
 * @since 06/August/2024  14:15
 **/
@Component
public class CategoryService implements BaseService<Category, Integer> {
    private final CategoryDAO dao;

    @Autowired
    public CategoryService(CategoryDAO dao) {
        this.dao = dao;
    }

    @Override
    public Integer save(Category domain) {
        return dao.save(domain);
    }

    @Override
    public void update(Category domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer uuid) {
        dao.delete(uuid);
    }

    @Override
    public Category findById(Integer uuid) {
        return dao.findById(uuid);
    }

    @Override
    public List<Category> findAll() {
        return dao.findAll();
    }

    public Set<String> findAllValues() {
        return findAll().stream().map(Category::getName).collect(Collectors.toSet());
    }
}

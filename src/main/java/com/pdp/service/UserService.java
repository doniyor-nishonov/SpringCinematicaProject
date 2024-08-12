package com.pdp.service;

import com.pdp.daos.UserDAO;
import com.pdp.domains.AuthUser;
import com.pdp.dto.LoginDTO;
import com.pdp.dto.SignupDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @author Doniyor Nishonov
 * @since 02/August/2024  20:12
 **/
@Component
public class UserService implements BaseService<AuthUser, Integer> {
    private final UserDAO dao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserDAO dao, PasswordEncoder passwordEncoder) {
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<AuthUser> login(LoginDTO dto) {
        if (Objects.isNull(dto.getUsername()) || Objects.isNull(dto.getPassword()))
            return Optional.empty();
        return Optional.ofNullable(dao.login(dto.getUsername(), dto.getPassword()));
    }

    public Integer signup(SignupDTO dto) {
        if (Objects.isNull(dto) || Objects.isNull(dto.getName()) || Objects.isNull(dto.getPhoneNumber())
                || Objects.isNull(dto.getPassword()) || Objects.isNull(dto.getUsername()))
            return null;
        AuthUser user = AuthUser.builder()
                .name(dto.getName())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .phoneNumber(dto.getPhoneNumber())
                .build();
        return save(user);
    }

    @Override
    public Integer save(AuthUser domain) {
        return dao.save(domain);
    }

    @Override
    public void update(AuthUser domain) {
        dao.update(domain);
    }

    @Override
    public void delete(Integer id) {
        dao.delete(id);
    }

    @Override
    public AuthUser findById(Integer id) {
        return dao.findById(id);
    }

    @Override
    public List<AuthUser> findAll() {
        return dao.findAll();
    }

    public Optional<AuthUser> findByUsername(String username) {
        return dao.findByUsername(username);
    }

    public List<AuthUser> findAllWithoutAdmin() {
        return dao.findAllWithoutAdmin();
    }

    public Optional<AuthUser> checkByPhoneNumber(String phoneNumber) {
        return Optional.ofNullable(dao.findByPhoneNumber(phoneNumber));
    }

    public void updatePassword(Integer userId, String password) {
        dao.updatePassword(userId,passwordEncoder.encode(password));
    }
}

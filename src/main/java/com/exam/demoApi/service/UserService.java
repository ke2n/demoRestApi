package com.exam.demoApi.service;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.exam.demoApi.domain.User;
import com.exam.demoApi.exception.CustomException;
import com.exam.demoApi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_DATA;
import static com.exam.demoApi.exception.ExceptionCode.NOT_FOUND_USER;
import static com.exam.demoApi.exception.ExceptionCode.SIGNUP_EXIST_USERNAME;
import static com.exam.demoApi.exception.ExceptionCode.SIGNUP_REQUIRED_PASSWORD;
import static com.exam.demoApi.exception.ExceptionCode.SIGNUP_REQUIRED_USERNAME;

/**
 * @author yunsung Kim
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User signin(User user) {

        if (user == null
            || StringUtils.isEmpty(user.getUsername())
            || StringUtils.isEmpty(user.getPassword())) {
            throw new CustomException(NOT_FOUND_USER);
        }

        User resultUser = repository.findByUsername(user.getUsername())
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));

        if (!BCrypt.checkpw(user.getPassword(), resultUser.getPassword())) {
            throw new CustomException(NOT_FOUND_USER);
        }

        return resultUser;
    }

    public User signup(User user) {

        if (user == null) {
            throw new CustomException(NOT_FOUND_DATA);
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            throw new CustomException(SIGNUP_REQUIRED_USERNAME);
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            throw new CustomException(SIGNUP_REQUIRED_PASSWORD);
        }
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new CustomException(SIGNUP_EXIST_USERNAME);
        }

        User newUser = User.builder()
            .username(user.getUsername())
            .password(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()))
            .build();

        return repository.save(newUser);
    }

    public User findByUsername(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new CustomException(NOT_FOUND_USER);
        }

        return repository.findByUsername(username)
            .orElseThrow(() -> new CustomException(NOT_FOUND_USER));
    }
}

package com.exam.demoApi.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exam.demoApi.domain.User;
import com.exam.demoApi.model.SecuInfo;
import com.exam.demoApi.service.SecurityService;
import com.exam.demoApi.service.UserService;

/**
 * @author yunsung Kim
 */
@RestController
@RequestMapping("/api/auth")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private UserService userService;

    @PostMapping("/signin")
    public SecuInfo signin(@RequestBody User user) {
        User resultUser = userService.signin(user);
        String accessToken = securityService.createUserKey(resultUser);

        return SecuInfo.builder()
            .accessToken(accessToken)
            .build();
    }

    @PostMapping("/signup")
    public SecuInfo signup(@RequestBody User user) {
        User resultUser = userService.signup(user);
        String accessToken = securityService.createUserKey(resultUser);

        return SecuInfo.builder()
            .accessToken(accessToken)
            .build();
    }

    @PostMapping("/refresh")
    public SecuInfo refresh(HttpServletRequest request) {
        String token = securityService.getTokenFromRequest(request);
        String name = securityService.getNameFromToken(token);

        User resultUser = userService.findByUsername(name);
        String accessToken = securityService.createUserKey(resultUser);

        return SecuInfo.builder()
            .accessToken(accessToken)
            .build();
    }
}

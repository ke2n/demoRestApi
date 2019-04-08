package com.exam.demoApi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.exam.demoApi.service.SecurityService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtInterceptor implements HandlerInterceptor {

    private final SecurityService securityService;

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        String token = securityService.getTokenFromRequest(req);
        String name = securityService.getNameFromToken(token);

        if (StringUtils.isEmpty(name)) {
            return false;
        }

        return true;
    }
}

package org.example.racekatteklubben.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse respons, Object Handler) throws Exception {
        Integer memberId = (Integer) request.getSession().getAttribute("memberId");

        if (memberId == null) {
            respons.sendRedirect("/auth/access-denied");
            return false;
        }

        return true;
    }
}

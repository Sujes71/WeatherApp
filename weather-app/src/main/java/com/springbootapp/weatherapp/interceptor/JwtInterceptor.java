package com.springbootapp.weatherapp.interceptor;

import com.springbootapp.weatherapp.service.component.JwtTokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    private final JwtTokenUtil jwtTokenUtil;
    @Autowired
    public JwtInterceptor(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token not provided");
            return false;
        }
        String jwt = token.substring(7);
        if (jwtTokenUtil.validateToken(jwt)) {
            return true;
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // This method is called after the request has been handled.
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        // This method is called after completing the response and rendering the view.
    }
}

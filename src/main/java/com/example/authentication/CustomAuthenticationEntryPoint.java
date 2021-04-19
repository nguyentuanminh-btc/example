package com.example.authentication;

import org.apache.tomcat.util.descriptor.web.ErrorPage;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorPage erorPage = new ErrorPage();
        erorPage.setErrorCode(401);
        erorPage.setLocation("/401");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "401");

    }
}

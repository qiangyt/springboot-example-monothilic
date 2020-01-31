package qiangyt.springboot_example.server.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import qiangyt.springboot_example.common.json.JsonHelper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Based on https://github.com/Smith-Cruise/Spring-Boot-Security-JWT-SPA
 *          org.inlighting.security.security.JwtAuthenticationFilter
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
        throws AuthenticationException {

        var username = request.getParameter("username");
        var password = request.getParameter("password");

        var token = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, token);
        
        return getAuthenticationManager().authenticate(token);
    }

    
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) 
        throws IOException, ServletException {
        
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.OK.value());

        User user = (User) authResult.getPrincipal();
        String token = JwtHelper.sign(user.getUsername(), user.getPassword());

        response.getWriter().write(JsonHelper.to("Bearer " + token));
    }


    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) 
        throws IOException, ServletException {
        
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        
        response.getWriter().write("failed to authenticate");
    }

}

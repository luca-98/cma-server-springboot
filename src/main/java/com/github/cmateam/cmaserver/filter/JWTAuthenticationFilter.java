package com.github.cmateam.cmaserver.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.github.cmateam.cmaserver.service.TokenAuthenticationService;
import com.github.cmateam.cmaserver.service.UserDetailsServiceImpl;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;

// Đọc token JWT từ tất cả request gửi lên
public class JWTAuthenticationFilter extends GenericFilterBean {

    private TokenAuthenticationService tokenAuthenticationService;
    private UserDetailsServiceImpl userDetailsService;

    public JWTAuthenticationFilter(TokenAuthenticationService tokenAuthenticationService,
            UserDetailsServiceImpl userDetailsService) {
        this.tokenAuthenticationService = tokenAuthenticationService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String jwt = tokenAuthenticationService.getJWTFromRequest((HttpServletRequest) request);
        String userName = tokenAuthenticationService.getUsernameFromJWT(jwt);

        if (userName == null) {
            chain.doFilter(request, response);
            return;
        }

        UserDetails currentUser = userDetailsService.loadUserByUsername(userName);
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null,
                currentUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        chain.doFilter(request, response);
    }
}

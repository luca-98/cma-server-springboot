package com.github.cmateam.cmaserver.service;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.github.cmateam.cmaserver.configuration.CmaConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class TokenAuthenticationService {

    Logger logger = LoggerFactory.getLogger(TokenAuthenticationService.class);

    private CmaConfig cmaConfig;

    public TokenAuthenticationService(CmaConfig cmaConfig) {
        this.cmaConfig = cmaConfig;
    }

    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + cmaConfig.getTokenExpiration());
        return Jwts.builder().setSubject(username).setIssuedAt(now).setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, cmaConfig.getTokenSecretKey()).compact();
    }

    public String getUsernameFromJWT(String token) {
        if (token == null) {
            // logger.error("The token was not found in the request");
            return null;
        }
        try {
            Claims claims = Jwts.parser().setSigningKey(cmaConfig.getTokenSecretKey())
                    .parseClaimsJws(token.replace(cmaConfig.getTokenPrefix(), "")).getBody();
            return claims.getSubject();
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public String getJWTFromRequest(HttpServletRequest request) {
        return request.getHeader(cmaConfig.getTokenHeaderString());
    }
}

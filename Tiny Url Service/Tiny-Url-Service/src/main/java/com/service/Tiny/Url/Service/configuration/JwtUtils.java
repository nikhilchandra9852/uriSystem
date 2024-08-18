package com.service.Tiny.Url.Service.configuration;


import com.service.Tiny.Url.Service.service.UserDetailsImp;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.adapter.UnknownAdviceTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger =  LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${expired}")
    private int jwtExpired;


    /**
     * Generate a JWT token based on the provided authentication.
     *
     * @param authentication The authentication object containing user details.
     * @return The generated JWT token as a string.
     */

    public String generateToken(Authentication authentication) {
// Get the user details from the authentication object
        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject(userDetailsImp.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+jwtExpired))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    /**
     * Extract the username from the given JWT token.
     *
     * @param token The JWT token.
     * @return The username extracted from the token.
     */
    public String getUserNameFromJwtToken(String token){
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build()
                .parseClaimsJws(token).getBody().getSubject();
    }


    /**
     * Validate the given JWT token.
     *
     * @param authToken The JWT token to validate.
     * @return True if the token is valid, false otherwise.
     */
    public boolean validateJwtToken(String authToken){
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parse(authToken);
            return true;
        }catch (MalformedJwtException e){
            logger.error("Invalid JWT Token {}",e.getMessage());
        }catch (ExpiredJwtException e){
            logger.error("JWT token is expired {}",e.getMessage());
        }catch (UnsupportedJwtException e){
            logger.error("Jwt token is unsupported {}",e.getMessage());
        }catch (IllegalArgumentException e){
            logger.error("Jwt claims it is empty String {}",e.getMessage());
        }
        return  false;

    }

}

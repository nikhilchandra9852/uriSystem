package com.service.Tiny.Url.Service.configuration;

import com.service.Tiny.Url.Service.entities.SignUp;
import com.service.Tiny.Url.Service.service.UserDetailsImplService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter to validate the JWT token and set user authentication in the security context.
 */
public class AuthTokenFiltering extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsImplService userDetailsImplService;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFiltering.class);
    /**
     * Filter method to process the JWT token and set authentication.
     *
     * @param request The HTTP request.
     * @param response The HTTP response.
     * @param filterChain The filter chain for further processing.
     * @throws ServletException If a servlet-related exception occurs.
     * @throws IOException If an input or output exception occurs.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // parse the JwtToken
            String jwt = parseJwt(request);
            if(jwt!=null && jwtUtils.validateJwtToken(jwt)){
                String username = jwtUtils.getUserNameFromJwtToken(jwt);
                //load user details
                UserDetails userDetails = userDetailsImplService.loadUserByUsername(username);

                //create a authentication token with the user Details
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                //Set additional details from the request
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


                // Set the authentication in the securityContext
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }catch (Exception e){
            logger.error("Connot set user authentication :{}",e);
        }
        filterChain.doFilter(request,response);
    }
    /**
     * Parse the JWT token from the Authorization header.
     *
     * @param request The HTTP request.
     * @return The JWT token if found, or null if not found.
     */
    private String parseJwt(HttpServletRequest request){
        // get the token using authorization
        String haedAuth = request.getHeader("Authorization");

        if(StringUtils.hasText(haedAuth) && haedAuth.startsWith("Bearer")){
            // Extract the JWT token from the header
            return haedAuth.substring(7);
        }return null;
    }


}

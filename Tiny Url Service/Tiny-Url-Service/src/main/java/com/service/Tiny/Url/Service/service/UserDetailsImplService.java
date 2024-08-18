package com.service.Tiny.Url.Service.service;

import com.service.Tiny.Url.Service.entities.SignUp;
import com.service.Tiny.Url.Service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of UserDetailsService to load user-specific data.
 */
@Service
public class UserDetailsImplService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;


    /**
     * Loads user details by username.
     *
     * @param username The username of the user.
     * @return UserDetails containing user information.
     * @throws UsernameNotFoundException if the user is not found.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SignUp signUp = userRepository.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("UserName is not found"));
        return UserDetailsImp.build(
                signUp
        );
    }


}

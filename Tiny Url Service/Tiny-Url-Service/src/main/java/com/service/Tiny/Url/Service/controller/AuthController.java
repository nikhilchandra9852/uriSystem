package com.service.Tiny.Url.Service.controller;


import com.service.Tiny.Url.Service.configuration.JwtUtils;
import com.service.Tiny.Url.Service.entities.Login;
import com.service.Tiny.Url.Service.entities.Roles;
import com.service.Tiny.Url.Service.entities.SignUp;
import com.service.Tiny.Url.Service.models.JwtResponse;
import com.service.Tiny.Url.Service.models.LoginRequest;
import com.service.Tiny.Url.Service.models.MessageResponse;
import com.service.Tiny.Url.Service.models.SignUpEntity;
import com.service.Tiny.Url.Service.repository.LoginRepository;
import com.service.Tiny.Url.Service.repository.UserRepository;
import com.service.Tiny.Url.Service.service.UserDetailsImp;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/shortservice/v1/api")
public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoginRepository loginRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;
    /**
     * Authenticate user and return a JWT token if successful.
     *
     * @param loginRequest The login request containing username and password.
     * @return A ResponseEntity containing the JWT response or an error message.
     */
    @GetMapping("/signin")
    public ResponseEntity<?> siginInUser(@Valid @RequestBody LoginRequest loginRequest){

        // Authenticate the user with usernma and password

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),loginRequest.getPassword())
        );

        // Set the authentication  in the security Context;

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // generate the jwt token ;
        String  jwt = jwtUtils.generateToken(authentication);

        UserDetailsImp userDetailsImp = (UserDetailsImp) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetailsImp.getId(),
                userDetailsImp.getUsername(),
                userDetailsImp.getEmail(),
                userDetailsImp.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
        );

    }

    @PostMapping("/signUp")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpEntity signUpEntity){
        // check whether user present or not.
        if(userRepository.existsByUsername(signUpEntity.getUsername())){
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // check whether user email is present or not
        if(userRepository.existsByEmail(signUpEntity.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        // now create the user
        SignUp signUp = new SignUp(signUpEntity.getUsername(),passwordEncoder.encode(signUpEntity.getPassword()),signUpEntity.getEmail());
        signUp.setRole(signUpEntity.getRole());
        String roles="";
        //default is UserRole
        if(signUp.getRole().isBlank()){
            Login login = loginRepository.findByName("ROLE_USER").orElseThrow(()->new RuntimeException("Error: Role is not Found"));
            roles=signUp.getRole();
        }else{
            if(signUp.getRole().equals("ROLE_ADMIN")){
                 loginRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(()->new RuntimeException("Error: Role is not found."));
                        roles=signUp.getRole();
            }else{
                loginRepository.findByName("ROLE_USER")
                        .orElseThrow(()->new RuntimeException("Error: Role is not found"));
                roles = signUp.getRole();
            }
        }
        signUp.setRole(roles);
        userRepository.save(signUp);

        return  ResponseEntity.ok(new MessageResponse(" User Registered Successfully"));

    }




}

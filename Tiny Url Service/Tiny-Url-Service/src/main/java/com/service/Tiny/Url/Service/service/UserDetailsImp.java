package com.service.Tiny.Url.Service.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.service.Tiny.Url.Service.entities.SignUp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
/**
 * Implementation of Spring Security's UserDetails interface for representing user details.
 */
public class UserDetailsImp implements UserDetails {
    private static final long serialVersionId=1L;
    private String id;
    private String username;
    private String email;
    @JsonIgnore // it will prevent serialization of the password
    private String password;

    private Collection< ? extends GrantedAuthority> authorities;

    public UserDetailsImp(String id, String username, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    /**
     * Builds a UserDetailsImpl instance from a User object.
     *
     * @param signUp The User object.
     * @return A UserDetailsImpl instance.
     */
    public static UserDetailsImp build(SignUp signUp){
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(signUp.getRole().toString()));

        return  new UserDetailsImp(
                signUp.getId(),
                signUp.getUsername(),
                signUp.getEmail(),
                signUp.getPassword(),
                authorities
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public boolean equals(Object o){
        if(this==o){
            return true;
        }
        if(o==null || getClass() !=o.getClass()){
            return false;
        }
        return  Objects.equals(id,((UserDetailsImp)o).id);
    }
}

package com.testToken.app.testToken.config;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.testToken.app.testToken.model.Role;
import com.testToken.app.testToken.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Provides a basic implementation of the UserDetails interface
 */
public class CustomUserDetails implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String username;
    private String password;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = translate(user.getRoles());
    }

    /**
     * Translates the List Role to a list GrantedAuthority
     */
    private Collection<? extends GrantedAuthority> translate(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            String name = role.getRole().toUpperCase();
            // MAKE SURE THAT ALL ROLES START WITH "ROLE_"
            if (!name.startsWith("ROLE_")){
                name = "ROLE_" + name;
            }
            authorities.add(new SimpleGrantedAuthority(name));
        }
        return authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

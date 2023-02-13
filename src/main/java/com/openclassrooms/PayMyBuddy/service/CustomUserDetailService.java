package com.openclassrooms.PayMyBuddy.service;

import com.openclassrooms.PayMyBuddy.dao.UserRepository;
import com.openclassrooms.PayMyBuddy.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public void CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>(1);
        authorities.add(new SimpleGrantedAuthority("USER"));

        if (user == null) {
            throw new UsernameNotFoundException("User with email [" + email + "] not found in the system");
        }

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities);
    }
}

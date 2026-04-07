package com.omkar.ecom.service;

import com.omkar.ecom.model.UserPrincipal;
import com.omkar.ecom.model.Users;
import com.omkar.ecom.repository.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepo repo;

    public MyUserDetailsService(UserRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("username not found");
        }
        return new UserPrincipal(user);
    }
}

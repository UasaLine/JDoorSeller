package com.jds.service;

import com.jds.entity.UserEntity;
import com.jds.model.Role;

import com.sun.istack.internal.NotNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<Role> roleList = new ArrayList<>();
        roleList.add(Role.USER);

        return UserEntity.builder()
                .username("admin")
                .password("$2a$10$DMD6ILHkU12.yg7Xup91XO/ByVVV5Y3G1c/lct8nZNAnxUS9gJ9Ei")
                .authorities(roleList)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build();
    }
}

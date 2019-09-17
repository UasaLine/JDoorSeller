package com.jds.service;

import com.jds.entity.UserEntity;
import com.jds.model.Role;

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



        return userHoldder(username);
    }

    public UserEntity userHoldder(String username){


        String password = "";
        if (username.equals("admin")){
            password = "$2a$10$DMD6ILHkU12.yg7Xup91XO/ByVVV5Y3G1c/lct8nZNAnxUS9gJ9Ei";
        }
        else if(username.equals("boss")){
            password = "$2a$10$QcbiL9jaTreUoRjhXpazwueV1krqQWxP11u/9prmzf7ugdhA7dEEq";
        }
        else if(username.equals("user")){
            password = "$2a$10$gUZSDZ3l2PgCKw0MFYhP2.pBRuF6w4Ejr/QGYbj8BoCrCG9lJpZLq";
        }

        if (!password.equals("")){
            List<Role> roleList = new ArrayList<>();
            roleList.add(Role.USER);

            return UserEntity.builder()
                    .username(username)
                    .password(password)
                    .authorities(roleList)
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .enabled(true)
                    .build();
        }

        return null;
    }

}

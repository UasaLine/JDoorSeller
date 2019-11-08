package com.jds.entity;

import com.jds.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "password")
    String password;

    @Column(name = "login")
    private String username;

    @Transient
    private List<Role> authorities;
    @Transient
    private boolean accountNonExpired;
    @Transient
    private boolean accountNonLocked;
    @Transient
    private boolean credentialsNonExpired;
    @Transient
    private boolean enabled;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seller", cascade = CascadeType.ALL)
    List<DoorsОrder> orders;

    public boolean isAdmin(){

        boolean isAdmin = false;
        for (Role role:authorities){
            if (role == Role.ADMIN){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

}

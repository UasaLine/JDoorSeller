package com.jds.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "login")
    String login;

    @Column(name = "password")
    String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "seller", cascade = CascadeType.ALL)
    List<DoorsОrder> orders;
}

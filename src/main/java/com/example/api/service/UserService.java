package com.example.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /**
         * Mock do usuario
         * Aqui o usuario esta mockado, mas pode ser criada
         * o dominio de Usuario e buscar o usuario do banco de dados.
         * Mas, nao vou fazer isso, pois a ideia é somente simular
         * um usuario com senha criptografadd e o ROLE definido.
         */
        return User
                .builder()
                .username("admin")
                .password(encoder.encode("1234"))
                .roles("ADMIN")
                .build();
    }
}

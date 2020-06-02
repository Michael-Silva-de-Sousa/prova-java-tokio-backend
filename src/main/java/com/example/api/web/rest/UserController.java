package com.example.api.web.rest;

import com.example.api.dto.CredentialDTO;
import com.example.api.dto.TokenDTO;
import com.example.api.exceptions.PasswordInvalidException;
import com.example.api.security.jwt.JwtService;
import com.example.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public JwtService jwtService;

    @PostMapping("/auth")
    public TokenDTO authenticate(@RequestBody CredentialDTO credentialDTO){
        try{
            UserDetails userAuth = userService.authenticate(
                    credentialDTO.getLogin(),
                    credentialDTO.getPassword());

            String token = jwtService.generedToken((User) User.builder()
                    .username(userAuth.getUsername())
                    .password(userAuth.getPassword())
                    .roles("ADMIN")
                    .build());

            return new TokenDTO(userAuth.getUsername(), token);
        } catch (UsernameNotFoundException | PasswordInvalidException e) {
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}

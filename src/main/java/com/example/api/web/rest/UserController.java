package com.example.api.web.rest;

import com.example.api.dto.CredentialDTO;
import com.example.api.dto.TokenDTO;
import com.example.api.exceptions.PasswordInvalidException;
import com.example.api.security.jwt.JwtService;
import com.example.api.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/users")
@Api("Api Users")
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    public JwtService jwtService;

    @PostMapping("/auth")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Permite autenticar o usuário na aplicação")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Token válido. Usuario autenticado com sucesso"),
            @ApiResponse(code = 401, message = "Token inválido. Credenciais inválidas")
    })
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

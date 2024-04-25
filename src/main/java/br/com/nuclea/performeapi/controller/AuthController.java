package br.com.nuclea.performeapi.controller;

import br.com.nuclea.performeapi.entity.dto.LoginDTO;
import br.com.nuclea.performeapi.security.UserPrincipal;
import br.com.nuclea.performeapi.service.TokenService;
import br.com.nuclea.performeapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    @CrossOrigin
    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody LoginDTO login){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(login.email(), login.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        var user = (UserPrincipal) authentication.getPrincipal();
        user.setPassword(null);
        user.setToken(tokenService.gerarToken(user));
        return ResponseEntity.ok(user);
    }

    @CrossOrigin
    @PutMapping(value = "/refresh-token")
    public ResponseEntity refreshToken(@RequestBody String token){
        return ResponseEntity.ok(tokenService.refreshToken(token));
    }
}

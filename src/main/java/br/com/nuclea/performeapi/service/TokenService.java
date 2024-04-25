package br.com.nuclea.performeapi.service;

import br.com.nuclea.performeapi.entity.User;
import br.com.nuclea.performeapi.repository.UserRepository;
import br.com.nuclea.performeapi.security.UserPrincipal;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;

@Service
public class TokenService {

    public static final String ISSUER_TICKE_POM = "TickePom";
    public static final Algorithm ALGORITHM = Algorithm.HMAC384("9a2bc1ce14af040ad17c13a7d445e280");
    public static final String GOOGLE_CLIENT_ID = "945198610733-bscsbcda9vhoih97vo2vd73sok7l9mid.apps.googleusercontent.com";

    @Autowired
    private UserRepository userRepository;

    public String gerarToken(UserPrincipal user) {
        return JWT.create()
                .withIssuer(ISSUER_TICKE_POM)
                .withSubject(user.getUsername())
                .withClaim("id", user.getId())
                .withExpiresAt(LocalDateTime.now()
                                .plusMinutes(2)
                                .toInstant(ZoneOffset.of("-03:00"))
                ).sign(ALGORITHM);
    }

    public String getSubject(String token){
        return JWT.require(ALGORITHM)
                .withIssuer(ISSUER_TICKE_POM)
                .build().verify(token).getSubject();
    }

    public UserPrincipal refreshToken(String token) {
        User user = userRepository.findByUsernameFetchRoles(getSubject(token));
        UserPrincipal userPrincipal = new UserPrincipal(user);
        userPrincipal.setPassword(null);
        userPrincipal.setToken(gerarToken(userPrincipal));
        return userPrincipal;
    }

}

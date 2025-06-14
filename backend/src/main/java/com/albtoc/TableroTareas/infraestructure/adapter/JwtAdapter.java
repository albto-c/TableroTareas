package com.albtoc.TableroTareas.infraestructure.adapter;

import com.albtoc.TableroTareas.application.ports.out.JwtOutService;
import com.albtoc.TableroTareas.domain.exceptions.HeaderAuthorizationException;
import com.albtoc.TableroTareas.domain.exceptions.TokenException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public class JwtAdapter implements JwtOutService {
    @Value("${private.key.jwt}")
    private String privateKey;

    @Value("${max.time.jwt}")
    private long maxTime;

    public static final int INDEX_TOKEN = 7;
    public static final String ISSUER = "ISSUER";
    public static final String AUTHORITY_CLAIM = "authority";

    public String generateToken(String username, String authority) {
        Date issuedDate = new Date(System.currentTimeMillis());
        Date expiresDate = new Date(issuedDate.getTime() + maxTime);
        Algorithm algorithm = Algorithm.HMAC256(privateKey);

        return JWT.create()
                .withSubject(username)
                .withIssuer(ISSUER)
                .withClaim(AUTHORITY_CLAIM, authority)
                .withIssuedAt(issuedDate)
                .withExpiresAt(expiresDate)
                .sign(algorithm);
    }

    public boolean isTokenValid(String token) {
        Algorithm algorithm = Algorithm.HMAC256(privateKey);
        JWTVerifier jwtVerifier = JWT.require(algorithm)
                .withIssuer(ISSUER)
                .build();
        try {
            jwtVerifier.verify(token);
            return true;
        } catch (Exception e) {
            //TODO Implement functionality to update the token
            return (e instanceof TokenExpiredException);
        }
    }

    public Optional<String> getTokenOfRequest(HttpServletRequest request) {
        Optional<String> token = Optional.ofNullable(request.getHeader("Authorization"));

        if (token.isEmpty() || token.get().length() < INDEX_TOKEN) {
            return Optional.empty();
        }
        return Optional.of(token.get().substring(INDEX_TOKEN));
    }

    public String getTokenOfHeaderAuthorization(String header) throws HeaderAuthorizationException {
        if (header == null || header.isEmpty()) {
            throw new HeaderAuthorizationException();
        }
        return header.substring(INDEX_TOKEN);
    }

    public String getUsernameOfToken(String token) throws TokenException {
        if (!isTokenValid(token)) {
            throw new TokenException();
        }
        return JWT.decode(token).getSubject();
    }

    public String getAuthorityOfToken(String token) throws TokenException {
        if (!isTokenValid(token)) {
            throw new TokenException();
        }
        return JWT.decode(token).getClaim(AUTHORITY_CLAIM).toString();
    }

}

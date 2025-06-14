package com.albtoc.TableroTareas.application.ports.out;

import com.albtoc.TableroTareas.domain.exceptions.HeaderAuthorizationException;
import com.albtoc.TableroTareas.domain.exceptions.TokenException;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface JwtOutService {
    String generateToken(String username, String authority);

    boolean isTokenValid(String token);

    Optional<String> getTokenOfRequest(HttpServletRequest request);

    String getTokenOfHeaderAuthorization(String header) throws HeaderAuthorizationException;

    String getUsernameOfToken(String token) throws TokenException;

    String getAuthorityOfToken(String token) throws TokenException;
}

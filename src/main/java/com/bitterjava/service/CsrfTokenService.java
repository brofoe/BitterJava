package com.bitterjava.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

@Service
public class CsrfTokenService {

    private static final String SESSION_ATTRIBUTE = CsrfTokenService.class.getName() + ".TOKEN";

    private final SecureRandom secureRandom = new SecureRandom();

    public String getToken(HttpSession session) {
        String token = (String) session.getAttribute(SESSION_ATTRIBUTE);
        if (token == null) {
            byte[] bytes = new byte[32];
            secureRandom.nextBytes(bytes);
            token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
            session.setAttribute(SESSION_ATTRIBUTE, token);
        }
        return token;
    }

    public void verify(HttpSession session, String submittedToken) {
        Object storedToken = session.getAttribute(SESSION_ATTRIBUTE);
        if (!(storedToken instanceof String token)
                || submittedToken == null
                || !MessageDigest.isEqual(
                        token.getBytes(StandardCharsets.UTF_8),
                        submittedToken.getBytes(StandardCharsets.UTF_8))) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid CSRF token.");
        }
    }
}

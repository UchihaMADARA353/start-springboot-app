package com.example.start_spring_app.service.impl.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class JwtService {
    private final JwtEncoder jwtEncoder;

    @Value("${spring.expiration}")
    private int durationToken;

    public String generateToken(Authentication authentication) {
        Instant instant = Instant.now();

        String scope = authentication.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));

        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .issuedAt(instant)
                .expiresAt(instant.plus(Duration.ofMinutes(durationToken)))
                .notBefore(instant)
                .audience(List.of("mobile-client", "web-client"))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();

        JwsHeader jwsHeader = JwsHeader.with(MacAlgorithm.HS256).build();

        Jwt jwt = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, jwtClaimsSet));
        return jwt.getTokenValue();
    }
}
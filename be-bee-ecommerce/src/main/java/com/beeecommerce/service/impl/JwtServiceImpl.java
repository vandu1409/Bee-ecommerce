package com.beeecommerce.service.impl;

import com.beeecommerce.constance.Role;
import com.beeecommerce.entity.Account;
import com.beeecommerce.repository.AccountRepository;
import com.beeecommerce.service.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final AccountRepository accountRepository;

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${app.auth.token.expiration.access}")
    private Long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${app.auth.token.expiration.resetPassword}")
    private Long RESET_PASSWORD_TOKEN_EXPIRATION_TIME;

    @Value("${app.auth.token.expiration.refresh}")
    private Long REFRESH_TOKEN_EXPIRATION_TIME;

    @Value("${app.auth.secretKey}")
    private String SECRET_KEY;

    @Override
    public String extractUsername(String token) {
        return extractClaim(
                token,
                claims -> claims
                        .get("username", String.class)
        );
    }


    @Override
    public Account extractAccount(String token) {
        Account account = new Account();

        Claims claims = extractAllClaim(token);

        Role role = Role.values()[claims.get("role", Integer.class)];
        String username = claims.get("username", String.class);
        Long id = claims.get("id", Long.class);

        account.setRole(role);
        account.setUsername(username);
        account.setId(id);

        return account;

    }


    @Override
    public String generateAccessToken(Account account) {
        return generateToken(convertToMap(account), SECRET_KEY, ACCESS_TOKEN_EXPIRATION_TIME);
    }


    @Override
    public String generateRefreshToken(Account account) {
        String refreshToken = generateToken(convertToMap(account), SECRET_KEY, REFRESH_TOKEN_EXPIRATION_TIME);

        String key = "refreshToken::" + account.getUsername();

        // TODO: Save refreshToken to redis
        redisTemplate.opsForValue().set(key, refreshToken, REFRESH_TOKEN_EXPIRATION_TIME, TimeUnit.MILLISECONDS);

        return refreshToken;

    }

    private static Map<String, Object> convertToMap(Account account) {
        Map<String, Object> claims = new HashMap<>();

        claims.put("username", account.getUsername());
        claims.put("role", account.getRole().ordinal());
        claims.put("id", account.getId());
        return claims;
    }


    @Override
    public String generateResetPasswordToken(Account account) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("username", account.getUsername());


        String secretKey = account.getSecretKey();


        return generateToken(claims, secretKey, RESET_PASSWORD_TOKEN_EXPIRATION_TIME);
    }

    @Override
    public boolean isValidToken(String token) {
        return !isExpired(token);
    }

    public Claims extractAllClaim(String token) {
        return extractAllClaim(token, SECRET_KEY);
    }

    public Claims extractAllClaim(String token, String secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    @Override
    public Account isValidateResetPasswordToken(String token) throws Exception {

        String key = extractUsernameFromResetPasswordToken(token);

        Account account = accountRepository
                .loadAccount(key)
                .orElseThrow(
                        () -> new EntityNotFoundException("Account not found")
                );

        String secretKey = account.getSecretKey();

        // So sanh secretKey trong token va secretKey trong database
        extractAllClaim(token, secretKey);

        return account;
    }


    private String generateToken(Map<String, Object> claims, String secretKey, Long expirationTime) {
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }


    private boolean isExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        Claims claims = extractAllClaim(token);
        return claimResolver.apply(claims);
    }

    private Key getSignInKey(String secretKey) {
        final byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsernameFromResetPasswordToken(String token) throws JsonProcessingException {
        String payloadEncode = token.substring(
                token.indexOf(".") + 1,
                token.lastIndexOf(".")
        );


        byte[] payloadBytes = Base64
                .getDecoder().decode(payloadEncode);

        String payload = new String(payloadBytes);

        TypeReference<HashMap<String, Object>> reference = new TypeReference<>() {
        };

        HashMap<String, Object> map = new ObjectMapper().readValue(payload, reference);

        return (String) map.get("username");
    }

}

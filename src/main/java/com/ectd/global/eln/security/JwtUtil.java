package com.ectd.global.eln.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private long jwtExpirationMs;

	@Value("${jwt.refreshExpiration}")
	private long refreshExpirationMs;

	@Value("${jwt.token.prefix}")
	private String tokenPrefix;

	@Value("${jwt.header}")
	private String header;

	private Key key;

	@PostConstruct
	void init() {

		this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String userId) {
		return buildToken(userId, jwtExpirationMs, "access");
	}

	public String generateRefreshToken(String userId) {
		return buildToken(userId, refreshExpirationMs, "refresh");
	}

	public boolean validateAccessToken(String token, String expectedUserId) {
		Claims c = parseClaims(token);
		return validateCommon(c, expectedUserId) && isAccessTokenClaims(c);
	}

	public boolean validateRefreshToken(String token, String expectedUserId) {
		Claims c = parseClaims(token);
		return validateCommon(c, expectedUserId) && isRefreshTokenClaims(c);
	}

	public boolean isAccessToken(String token) {
		return isAccessTokenClaims(parseClaims(token));
	}

	public boolean isRefreshToken(String token) {
		return isRefreshTokenClaims(parseClaims(token));
	}

	public String extractUserId(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> resolver) {
		return resolver.apply(parseClaims(token));
	}

	public String getHeader() {
		return header;
	}

	public String getTokenPrefix() {
		return tokenPrefix;
	}

	public String stripTokenPrefix(String value) {
		if (value == null)
			return null;
		String p = tokenPrefix == null ? "" : tokenPrefix;
		return value.startsWith(p) ? value.substring(p.length()).trim() : value.trim();
	}

	private String buildToken(String userId, long ttlMs, String tokenType) {
		final Date now = new Date();
		return Jwts.builder().setSubject(userId).setIssuedAt(now).setExpiration(new Date(now.getTime() + ttlMs))
				.claim("tokenType", tokenType).signWith(key, SignatureAlgorithm.HS256).compact();
	}

	private Claims parseClaims(String maybeWithPrefix) {
		String raw = stripTokenPrefix(maybeWithPrefix);
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(raw).getBody();
	}

	private boolean validateCommon(Claims c, String expectedUserId) {
		return expectedUserId != null && expectedUserId.equals(c.getSubject()) && c.getExpiration() != null
				&& c.getExpiration().after(new Date());
	}

	private boolean isAccessTokenClaims(Claims c) {
		return "access".equals(c.get("tokenType", String.class));
	}

	private boolean isRefreshTokenClaims(Claims c) {
		return "refresh".equals(c.get("tokenType", String.class));
	}
}

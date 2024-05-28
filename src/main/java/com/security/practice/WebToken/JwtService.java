package com.security.practice.WebToken;

import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET = "FA2C032DDA981FD97D6185BDDC0B274AC6F1BE384D5B3F2199E0FE744FA85ADBCFE83E56488EB72EC1EB4F09BE08DBCE45A69D7FCE444CB0BC600C34F212D366";
	public static final long VALIDITY = TimeUnit.DAYS.toMillis(1);
	
	public String generateToken(UserDetails userDetails) {
		Map<String, String> claims = new HashMap<>();
		claims.put("iss", "http://localhost:8080/ems.com");
//		claims.put("name", "kshitiz");
		return Jwts
				.builder()
				.claims(claims)
				.subject(userDetails.getUsername())
				.issuedAt(Date.from(Instant.now()))
				.expiration(Date.from(Instant.now().plusMillis(VALIDITY)))
				.signWith(generateKey())
				.compact();
		
	}
	private SecretKey generateKey() {
		byte[] decodedKey = Base64.getDecoder().decode(SECRET);
		return Keys.hmacShaKeyFor(decodedKey);
	}
	
	public String extractUsername(String jwt) {
		Claims claims =  getclClaims(jwt);
		return claims.getSubject();
	}
	
	private Claims getclClaims(String jwt) {
		return Jwts
				.parser()
				.verifyWith(generateKey())
				.build()
				.parseSignedClaims(jwt)
				.getPayload();
	}
	
	public boolean isTokenValid(String jwt) {
		Claims claims = getclClaims(jwt);
		return claims
				.getExpiration()
				.after(Date.from(Instant.now()));
	}
}

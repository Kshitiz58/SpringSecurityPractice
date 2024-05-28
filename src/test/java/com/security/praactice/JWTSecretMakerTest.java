package com.security.praactice;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;


import io.jsonwebtoken.Jwts;
import jakarta.xml.bind.DatatypeConverter;

public class JWTSecretMakerTest {

	@Test
	public void generateSecretKey() {
		SecretKey skey = Jwts.SIG.HS512.key().build();
		String key = DatatypeConverter.printHexBinary(skey.getEncoded());
		System.out.printf("\nskey = [%s]\n", key);
	}
}

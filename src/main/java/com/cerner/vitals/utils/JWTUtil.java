package com.cerner.vitals.utils;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.binary.Base64;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JWTUtil {
	private static String secret = "SAM-1997-MCA-SEC";
	static byte[] encoded = Base64.encodeBase64(secret.getBytes());

	public static String generateToken(String id, String subject) {

		String token = Jwts.builder().setId(id).setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(50)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes(Charset.forName("UTF-8")))
				.compact();

		return token;

	}

	public static Claims getClaims(String token) {

		return Jwts.parser().setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();

	}

	public static String getSubject(String token) {
		return getClaims(token).getSubject();
	}

	public static String getId(String token) {
		return getClaims(token).getId();
	}

}
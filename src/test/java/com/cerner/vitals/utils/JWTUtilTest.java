package com.cerner.vitals.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.jboss.logging.Logger;
import org.junit.Test;

import io.jsonwebtoken.Claims;

public class JWTUtilTest {

	private Logger log = Logger.getLogger(this.getClass());

	@Test
	public void generateToken() {
		log.info("Starting execution of generateToken");
		String expectedValue = "generated_token";
		String id = "";
		String subject = "";

		String actualValue = JWTUtil.generateToken(id, subject);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		assertEquals(!expectedValue.isEmpty(), !actualValue.isEmpty());
	}

	@Test
	public void getClaims() {
		log.info("Starting execution of getClaims");

		String id = "";
		String subject = "";
		String token = JWTUtil.generateToken(id, subject);
		Claims actualValue = JWTUtil.getClaims(token);
		assertFalse(actualValue.isEmpty());
	}

	@Test
	public void getSubject() {
		String id = "";
		String subject = "test_subject";
		String token = JWTUtil.generateToken(id, subject);

		String actualValue = JWTUtil.getSubject(token);
		assertFalse(actualValue.isEmpty());
	}

	@Test
	public void getId() {
		String id = "123";
		String subject = "";
		String token = JWTUtil.generateToken(id, subject);

		String actualValue = JWTUtil.getId(token);
		assertFalse(actualValue.isEmpty());
	}
}

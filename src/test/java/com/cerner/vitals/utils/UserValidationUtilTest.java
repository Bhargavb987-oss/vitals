package com.cerner.vitals.utils;

import static org.junit.Assert.assertEquals;

import org.jboss.logging.Logger;
import org.junit.Test;

import jakarta.ws.rs.core.Response;

public class UserValidationUtilTest {

	private Logger log = Logger.getLogger(this.getClass());

	@Test
	public void getUserRole() {
		log.info("Starting execution of getUserRole");
		String expectedValue = "admin";
		String user = "user1";

		UserValidationUtil uservalidationutil = new UserValidationUtil();
		String actualValue = uservalidationutil.getUserRole(user);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		assertEquals(expectedValue, actualValue);
	}

	@Test
	public void isCredententialsValid() {
		log.info("Starting execution of isCredententialsValid");
		Response expectedValue = null;
		String user = "user1";
		String pwd = "pwd";

		UserValidationUtil uservalidationutil = new UserValidationUtil();
		Response actualValue = uservalidationutil.isCredententialsValid(user, pwd);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		assertEquals(200, actualValue.getStatus());
	}
}

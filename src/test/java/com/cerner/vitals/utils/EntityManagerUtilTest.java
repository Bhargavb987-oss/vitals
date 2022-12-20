package com.cerner.vitals.utils;

import static org.junit.Assert.assertFalse;

import javax.persistence.EntityManager;

import org.jboss.logging.Logger;
import org.junit.Test;

public class EntityManagerUtilTest {

	private Logger log = Logger.getLogger(this.getClass());

	EntityManager entityManager = EntityManagerUtil.getInstance().getManager();
	
	@Test
	public void getManager() {
		log.info("Starting execution of getManager");
		EntityManager actualValue = EntityManagerUtil.getInstance().getManager();

		assertFalse(actualValue.getProperties().isEmpty());
	}
}
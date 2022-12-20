package com.cerner.vitals.controller;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import com.cerner.vitals.DAO.impl.UserDaoImpl;
import com.cerner.vitals.utils.UserValidationUtil;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;

public class ControllerTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(Controller.class,UserValidationUtil.class,UserDaoImpl.class);
	}

	@Test
	public void testAuthGen() {
		Form data = new Form();
		data.param("user1", "pwd");
		Response response = target("app/genToken").request().post(Entity.form(data));
		assertEquals(401, response.getStatus());
	}
	
	@Test
	public void getByPatientId() {
		Response response = target("app/getByPatientId").request().get();
		assertEquals(202, response.getStatus());
	}
	
	@Test
	public void getByDateRange() {
		Response response = target("app/getByDateRange").request().get();
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void getByRecentlyStored() {
		Response response = target("app/getByRecentlyStored").request().get();
		assertEquals(202, response.getStatus());
	}
	
	@Test
	public void getByVitalId() {
		Response response = target("app/getByVitalId").request().get();
		assertEquals(202, response.getStatus());
	}
	
	@Test
	public void addVitalId() {
		Form data = new Form();
		data.param("user1", "pwd");
		Response response = target("app/addVitalId").request().post(Entity.form(data));
		assertEquals(415, response.getStatus());
	}
}

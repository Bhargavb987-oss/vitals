package com.cerner.vtals.controller;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.vitals.DAO.impl.UserDaoImpl;
import com.cerner.vitals.controller.Controller;
import com.cerner.vitals.utils.UserValidationUtil;

import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class ControllerTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(Controller.class);
	}

	@Mock
	UserValidationUtil userUtil;
	
	@Mock 
	UserDaoImpl mockUserDaoImpl;
	
	@Test
	public void testAuthGen() {
		Form data = new Form();
		data.param("user1", "pwd");
		Response response = target("app/genToken").request().post(Entity.form(data));
		assertEquals(401, response.getStatus());
	}
	
}

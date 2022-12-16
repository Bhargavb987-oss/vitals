package com.cerner.vtals.filter;

import static org.junit.Assert.assertEquals;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.cerner.vitals.controller.Controller;
import com.cerner.vitals.filter.SecurityFilter;

import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;

@RunWith(MockitoJUnitRunner.class)
public class SecurityFilterTest extends JerseyTest {

	@Override
	protected Application configure() {
		return new ResourceConfig(Controller.class,SecurityFilter.class);
	}

	@Test
	public void testAuthGen() {
		Form data = new Form();
		data.param("user1", "pwd");
		Response response = target("/app/getByPatientId").queryParam("id", 1).request().get();
		assertEquals(400, response.getStatus());
	}
	
	@Test
	public void testRolesAllowed() {
		Form data = new Form();
		data.param("user1", "pwd");
		Response response = target("/app/getByPatientId").queryParam("id", 1).request().header(HttpHeaders.AUTHORIZATION, "Bearer " + "... encoded token ...").get();
		assertEquals(401, response.getStatus());
	}

}

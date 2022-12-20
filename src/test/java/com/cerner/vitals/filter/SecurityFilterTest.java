package com.cerner.vitals.filter;

import static org.junit.Assert.assertNotNull;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.jboss.logging.Logger;
import org.junit.Test;
import org.mockito.Mock;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Application;
public class SecurityFilterTest extends JerseyTest{

	private Logger log = Logger.getLogger(this.getClass());
	
	@Override
	protected Application configure()  {
		return new ResourceConfig(SecurityFilter.class,ResourceInfo.class);
	}
	@Mock
	private ResourceInfo recource;
	
	@Mock
    private ResourceInfo resourceInfo;
	
	SecurityFilter filter;
	@Mock
    private ContainerRequestContext context;

	@Test  
	public void filterException(){  
		try {
			log.info("Starting execution of filter");
			ContainerRequestContext req = null; 

			SecurityFilter securityfilter  =new SecurityFilter(); 
			securityfilter.filter(req);
		} catch (Exception exception) {
			log.error("Exception in execution offilter-"+exception,exception);
			exception.printStackTrace();
			assertNotNull(exception.getMessage());
		}
	}  
	
}

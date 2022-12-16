package com.cerner.vitals.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.cerner.vitals.utils.JWTUtil;
import com.cerner.vitals.utils.UserValidationUtil;

import jakarta.annotation.Priority;
import jakarta.annotation.security.DenyAll;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ResourceInfo;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class SecurityFilter implements ContainerRequestFilter{
	@Context
	private HttpHeaders header;

	@Context
	private ResourceInfo recource;

	@Override
	public void filter(ContainerRequestContext req) throws IOException {

		Method method=recource.getResourceMethod();
		UserValidationUtil userUtil = new UserValidationUtil();

		if(!method.isAnnotationPresent(PermitAll.class))
		{
			//FOR ROLES ALLOWED ----------------------------------
			if(method.isAnnotationPresent(RolesAllowed.class)) {
				//IF NO AUTHORIZATION DETAILS --------------------------------
				List<String> authorization=header.getRequestHeader("Authorization");
				if(authorization==null || authorization.isEmpty()){
					req.abortWith(
							Response
							.status(Status.BAD_REQUEST)
							.entity("-- NO AUTHORIZATION DETAILS FOUND --")
							.build());
				}
				// IF AUTHORIZATION DETAILS ARE THERE ------------------------------
				else {
					try {
						String token=authorization.get(0);
						String trimmedToken = token.replace("Bearer ","");
						String user=JWTUtil.getSubject(trimmedToken);
						String userRole=userUtil.getUserRole(user);
						String[] mRoles=method.getAnnotation(RolesAllowed.class).value();
						List<String> methodRoles=Arrays.asList(mRoles);

						// USER ROLE MISMATCH ------------------------------
						if(!methodRoles.contains(userRole))
							req.abortWith(
									Response
									.status(Status.FORBIDDEN)
									.entity("--- USER CAN'T ACCESS THIS METHOD ---")
									.build()
									);
					} catch (Exception e) {
						//IF TOKEN IS NOT VALID OR EXPIRED --------------------------
						req.abortWith(
								Response
								.status(Status.UNAUTHORIZED)
								.entity("--- INVALID TOKEN FOUND ---")
								.build()
								);
						e.printStackTrace();
					}
				}
			}
		}
	}
}
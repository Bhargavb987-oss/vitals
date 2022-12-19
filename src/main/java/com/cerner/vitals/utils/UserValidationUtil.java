package com.cerner.vitals.utils;

import java.util.Optional;

import org.jboss.logging.Logger;

import com.cerner.vitals.DAO.impl.UserDaoImpl;
import com.cerner.vitals.model.User;

import jakarta.ws.rs.core.Response;

public class UserValidationUtil {

	Logger logger = Logger.getLogger(UserValidationUtil.class.getName());
	User currentUser = new User();
	UserDaoImpl userDao = new UserDaoImpl();
	public String getUserRole(String user) {
		logger.info("Querying for user role: "+user);
		return Optional.ofNullable(userDao.queryRole(user)).get().getRole();
	}

	public Response isCredententialsValid(String user, String pwd) {
		String data=null;
		if(userDao.queryUserAndPwd(user, pwd)) {
			data=JWTUtil.generateToken(user, user);
			logger.info("Generated token for user: "+user);
			return Response.status(Response.Status.OK).entity(data).build();
		}
		else {
			logger.error("Incorrect uesrname or password");
			data="INVALID USER/PASSWORD for username: '"+user+"'";
			return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
		}
	}

}

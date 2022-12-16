package com.cerner.vitals.utils;

import java.util.Optional;

import com.cerner.vitals.DAO.impl.UserDaoImpl;
import com.cerner.vitals.model.User;

import jakarta.ws.rs.core.Response;

public class UserValidationUtil {

	User currentUser = new User();
	UserDaoImpl userDao = new UserDaoImpl();
	public String getUserRole(String user) {
		return Optional.ofNullable(userDao.queryRole(user)).get().getRole();
	}

	public Response isCredententialsValid(String user, String pwd) {
		String data=null;
		if(userDao.queryUserAndPwd(user, pwd)) {
			data=JWTUtil.generateToken(user, user);
			return Response.status(Response.Status.OK).entity(data).build();
		}
		else {
			data="INVALID USER/PASSWORD for username: '"+user+"'";
			return Response.status(Response.Status.UNAUTHORIZED).entity(data).build();
		}
	}

}

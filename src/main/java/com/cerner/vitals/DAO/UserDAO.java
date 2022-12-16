package com.cerner.vitals.DAO;

import com.cerner.vitals.model.User;

public interface UserDAO {

	boolean queryUserAndPwd(String user, String password);
	User queryRole(String user);
}

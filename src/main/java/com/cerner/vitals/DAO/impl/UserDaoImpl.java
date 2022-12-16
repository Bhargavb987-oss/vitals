package com.cerner.vitals.DAO.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cerner.vitals.DAO.UserDAO;
import com.cerner.vitals.model.User;
import com.cerner.vitals.utils.EntityManagerUtil;

public class UserDaoImpl implements UserDAO{
	
	private EntityManager entityManager = EntityManagerUtil.getInstance().getManager();

	@Override
	public boolean queryUserAndPwd(String name, String password) {
		TypedQuery<User> query = entityManager.createQuery("FROM com.cerner.vitals.model.User v WHERE v.name = :name AND v.password = :password", User.class)
				.setParameter("name",name)
				.setParameter("password",password);
		List<User> resultList = query.getResultList();
		
		return !resultList.isEmpty();
	}
	
	@Override
	public User queryRole(String name) {
		TypedQuery<User> query = entityManager.createQuery("FROM com.cerner.vitals.model.User v WHERE v.name = :name", User.class)
				.setParameter("name",name);
		return query.getSingleResult();
	}

}

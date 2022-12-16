package com.cerner.vitals.DAO.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.cerner.vitals.DAO.VitalDAO;
import com.cerner.vitals.model.Patient;
import com.cerner.vitals.model.Vitals;
import com.cerner.vitals.utils.EntityManagerUtil;

public class VitalDAOImpl<T> implements VitalDAO<T>{

	private EntityManager entityManager = EntityManagerUtil.getInstance().getManager();
	
	@Override
	public List<Vitals> queryAllVitals(int id) throws Exception {
		
		TypedQuery<Vitals> query = entityManager.createQuery("FROM com.cerner.vitals.model.Vitals v WHERE v.patient_id = :id", Vitals.class).setParameter("id",String.valueOf(id));
		List<Vitals> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Vitals> queryByDateRange(String fromDate, String toDate) {
		TypedQuery<Vitals> query = entityManager.createQuery("from com.cerner.vitals.model.Vitals where last_updated BETWEEN :fromDate AND :toDate", Vitals.class).setParameter("fromDate",fromDate).setParameter("toDate",toDate);
		List<Vitals> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Vitals> queryByRecentlyUpdated(int id) {
		TypedQuery<Vitals> query = entityManager.createQuery("FROM com.cerner.vitals.model.Vitals v WHERE v.patient_id = :id order by v.last_updated", Vitals.class).setParameter("id",String.valueOf(id)).setMaxResults(2);
		List<Vitals> resultList = query.getResultList();
		return resultList;
	}

	@Override
	public List<Vitals> queryByVitalId(int patientId, List<Integer> vitalIds) {
		String queryFinal = "FROM com.cerner.vitals.model.Vitals v WHERE v.patient_id= :patientId AND v.vital_id in (vitalIds)".replace("vitalIds", vitalIds.stream().map(e -> String.valueOf(e)).collect(Collectors.joining(",")));
		List<Vitals> query = entityManager.createQuery(queryFinal, Vitals.class).setParameter("patientId",String.valueOf(patientId)).getResultList();
//		List<Vitals> resultList = query.getResultList();
		return query;
	}

	@Override
	public Patient queryAddPatient(Patient details) {
		entityManager.persist(details);
		entityManager.getTransaction().commit();
		return null;
	}

}

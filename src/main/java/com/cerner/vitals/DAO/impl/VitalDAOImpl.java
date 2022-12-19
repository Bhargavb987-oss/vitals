package com.cerner.vitals.DAO.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.jboss.logging.Logger;

import com.cerner.vitals.DAO.VitalDAO;
import com.cerner.vitals.model.Vitals;
import com.cerner.vitals.utils.EntityManagerUtil;

import jakarta.ws.rs.core.Response;

public class VitalDAOImpl<T> implements VitalDAO<T>{

	Logger logger = Logger.getLogger(VitalDAOImpl.class.getName());
	private EntityManager entityManager = EntityManagerUtil.getInstance().getManager();
	
	@Override
	public List<Vitals> queryAllVitals(int id) {
		List<Vitals> resultList = new ArrayList<>();
		try {
			TypedQuery<Vitals> query = entityManager.createQuery("FROM com.cerner.vitals.model.Vitals v WHERE v.patient_id = :id", Vitals.class).setParameter("id",String.valueOf(id));
			resultList = query.getResultList();
		} catch (Exception e) {
			logger.error("Failed during querying all Vitals: "+e.getMessage());
		}
		return resultList;
	}

	@Override
	public List<Vitals> queryByDateRange(String fromDate, String toDate) {
		List<Vitals> resultList = new ArrayList<>();
		try {
			TypedQuery<Vitals> query = entityManager.createQuery("from com.cerner.vitals.model.Vitals where last_updated BETWEEN :fromDate AND :toDate", Vitals.class).setParameter("fromDate",fromDate).setParameter("toDate",toDate);
			resultList = query.getResultList();
		} catch (Exception e) {
			logger.error("Failed during querying all Vitals for date range: "+e.getMessage());
		}
		return resultList;
	}

	@Override
	public List<Vitals> queryByRecentlyUpdated(int id) {
		List<Vitals> resultList = new ArrayList<>();
		try {
			TypedQuery<Vitals> query = entityManager.createQuery("FROM com.cerner.vitals.model.Vitals v WHERE v.patient_id = :id order by v.last_updated", Vitals.class).setParameter("id",String.valueOf(id)).setMaxResults(2);
			resultList = query.getResultList();
		} catch (Exception e) {
			logger.error("Failed during querying all Vitals for recently updated: "+e.getMessage());
		}
		return resultList;
	}

	@Override
	public List<Vitals> queryByVitalId(int patientId, List<Integer> vitalIds) {
		List<Vitals> resultList = new ArrayList<>();
		try {
			String queryFinal = "FROM com.cerner.vitals.model.Vitals v WHERE v.patient_id= :patientId AND v.vital_id in (vitalIds)".replace("vitalIds", vitalIds.stream().map(e -> String.valueOf(e)).collect(Collectors.joining(",")));
			resultList = entityManager.createQuery(queryFinal, Vitals.class).setParameter("patientId",String.valueOf(patientId)).getResultList();
		} catch (Exception e) {
			logger.error("Failed during querying data of Vital ID: "+e.getMessage());
		}
		return resultList;
	}

	@Override
	public Response queryAddPatient(Vitals vitalDetails) {
		try {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");  
			LocalDateTime now = LocalDateTime.now();  
			vitalDetails.setFirst_updated(dtf.format(now));
			beginTransaction();
			entityManager.persist(vitalDetails);
			closeTransaction();
			return Response.status(Response.Status.OK).entity("Added Patient Vitals").build();
		} catch (RollbackException e) {
			logger.error("Failed as duplicate ID: "+e.getMessage());
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Duplicate ID number.").build();
		} catch (Exception e) {
			logger.error("Failed to add patient vitals: "+e.getMessage());
			return Response.status(Response.Status.BAD_REQUEST).entity("Error while creating adding patient vitals").build();
		}
	}


	@Override
	public boolean queryDeletePatientVitals(int vitalId) {
		try {
			beginTransaction();
			Vitals orderDetails = entityManager.find(Vitals.class,String.valueOf(vitalId));
			entityManager.remove(orderDetails);
			closeTransaction();
			return true;
		} catch (Exception e) {
			logger.error("Failed query to delete vitals: "+e.getMessage());
			return false;
		}
    }
	
	@Override
	public boolean queryUpdatePatientVitals(Vitals vitalDetails) {
		try {
			//Update Count by 1
			updateCount(vitalDetails);
			
			beginTransaction();
			entityManager.merge(vitalDetails);
			closeTransaction();
			return true;
		} catch (Exception e) {
			logger.error("Failed query to update vitals: "+e.getMessage());
			return false;
		}
	}
	
	

	private void updateCount(Vitals vitalDetails) {
		vitalDetails.setUpdateCount(vitalDetails.getUpdateCount()+1);
	}

	private void closeTransaction() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}

	private void beginTransaction() {
		entityManager.getTransaction().begin();
	}


}

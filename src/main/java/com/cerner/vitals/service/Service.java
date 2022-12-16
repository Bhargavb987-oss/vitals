package com.cerner.vitals.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import com.cerner.vitals.DAO.impl.VitalDAOImpl;
import com.cerner.vitals.model.Patient;
import com.cerner.vitals.model.Vitals;

import jakarta.ws.rs.core.Response;

public class Service extends VitalDAOImpl<Vitals> {

	public List<Vitals> findAllPatients(int id) throws Exception {
		return queryAllVitals(id);
	}

	public List<Vitals> findByDateRange(String fromDate, String toDate) {
		validateDate(fromDate);
		validateDate(toDate);
		return queryByDateRange(fromDate,toDate);
	}

	public List<Vitals> findByRecentlyStored(int id) {
		return queryByRecentlyUpdated(id);
	}

	public List<Vitals> findByVitalId(int patientId, List<Integer> vitalIds) {
		return queryByVitalId(patientId, vitalIds);
	}
	
	
	private void validateDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//	    formatter.setLenient(false);
	    try {
	        formatter.parse(date);
	    } catch (ParseException e) {
	       System.out.println("invalid date");
	    }
		
	}

	public Response addPatientVitals(Patient details) {
		queryAddPatient(details);
		
		
		return Response.notAcceptable(Collections.emptyList()).build();
		
	}

}

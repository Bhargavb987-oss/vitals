package com.cerner.vitals.DAO;

import java.util.List;

import com.cerner.vitals.model.Vitals;

import jakarta.ws.rs.core.Response;

public interface VitalDAO<T> {

	List<Vitals> queryAllVitals(int id) throws Exception;
	List<Vitals> queryByDateRange(String fromDate, String toDate);
	List<Vitals> queryByRecentlyUpdated(int id);
	List<Vitals> queryByVitalId(int patientId, List<Integer> vitalIds);
	Response queryAddPatient(Vitals details);
	boolean queryDeletePatientVitals(int id);
	boolean queryUpdatePatientVitals(Vitals vitalDetails);

}
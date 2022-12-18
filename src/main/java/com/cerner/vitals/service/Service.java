package com.cerner.vitals.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.cerner.vitals.DAO.impl.VitalDAOImpl;
import com.cerner.vitals.model.Vitals;

import jakarta.ws.rs.core.Response;

public class Service extends VitalDAOImpl<Vitals> {

	public Response findAllPatients(int id) throws Exception {
		List<Vitals> resultList = queryAllVitals(id);
		if(!resultList.isEmpty()) {
			return Response.status(Response.Status.OK).entity("Vitals for Patient ID: "+resultList).build();
		}
		return Response.status(Response.Status.ACCEPTED).entity("No vitals found for patient ID: "+id).build();

	}

	public Response findByDateRange(String fromDate, String toDate) {
		if(!validateDate(fromDate) || !validateDate(toDate)) {
			return Response.status(400, "Invalid Date format given ").build();
		}
		List<Vitals> resultList = queryByDateRange(fromDate,toDate);
		if(!resultList.isEmpty()) {
			return Response.status(Response.Status.OK).entity("Data for vital date range: "+resultList).build();
		}
		return Response.status(Response.Status.ACCEPTED).entity("No data present for the date range: "+fromDate+" to "+toDate).build();
	}

	public Response findByRecentlyStored(int id) {
		List<Vitals> resultList = queryByRecentlyUpdated(id);
		if(!resultList.isEmpty()) {
			return Response.status(Response.Status.OK).entity("Data for recently stored vitals: "+resultList).build();
		}
		return Response.status(Response.Status.ACCEPTED).entity("No data present for recently stored.").build();
	}

	public Response findByVitalId(int patientId, List<Integer> vitalIds) {
		List<Vitals> resultList = queryByVitalId(patientId, vitalIds);
		if(!resultList.isEmpty()) {
			return Response.status(Response.Status.OK).entity("Data for vital ID/IDs: "+resultList).build();
		}
		return Response.status(Response.Status.ACCEPTED).entity("No data present for the ID/IDs: "+resultList).build();
	}


	public Response addPatientVitals(Vitals vitalDetails) {
		Response validation = validDataChecks(vitalDetails);
		if(validation!=null) {
			return validation;
		} return queryAddPatient(vitalDetails);
	}

	public Response deletePatientVitals(int vitalId) {
		if(queryDeletePatientVitals(vitalId)) {
			return Response.status(Response.Status.OK).entity("Deleted vital ID: "+vitalId).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Unable to delete using vital ID: "+vitalId).build();
	}

	public Response updatePatientVitals(Vitals vitalDetails) {
		Response validation = validDataChecks(vitalDetails);
		if(validation!=null) {
			return validation;
		} else if(queryUpdatePatientVitals(vitalDetails)) {
			Response.status(Response.Status.OK).entity("Updated vital ID: "+vitalDetails).build();
		}
		return Response.status(Response.Status.BAD_REQUEST).entity("Error while updating : "+vitalDetails).build();
	}

	
	private boolean validateDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setLenient(false);
		try {
			if(date!=null) {
				formatter.parse(date);
				return true;
			}
		} catch (ParseException e) {
			System.out.println(e.getMessage());
			return false;
		}
		return false;
	}
	
	Response validDataChecks(Vitals vitalDetails){
		if(vitalDetails.getName()==null || vitalDetails.getName().matches("[0-9]+")) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Name cannot be empty or consist of special chars : "+vitalDetails.getName()).build();
		}
		if(vitalDetails.getPatient_id()==null || !vitalDetails.getPatient_id().matches("[0-9]+")) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Patient ID cannot be empty or consist of special chars : "+vitalDetails.getPatient_id()).build();
		}
		if(vitalDetails.getVital_id()==null || !vitalDetails.getVital_id().matches("[0-9]+")) {
			return Response.status(Response.Status.BAD_REQUEST).entity("Vital ID cannot be empty or consist of special chars : "+vitalDetails.getVital_id()).build();
		}
		if(!validateDate(vitalDetails.getFirst_updated()) || !validateDate(vitalDetails.getLast_updated())) {
			return Response.status(400, "Invalid Date format given ").build();
		}
		return null;
		
		
	}
	
}

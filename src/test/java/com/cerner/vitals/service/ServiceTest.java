package com.cerner.vitals.service;

import java.util.List;

import org.jboss.logging.Logger;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;

import com.cerner.vitals.model.Patient;
import com.cerner.vitals.model.Vitals;

import jakarta.ws.rs.core.Response;

public class ServiceTest {

	private Logger log = Logger.getLogger(this.getClass());

	@Test
	public void findAllPatients() throws Exception {
		log.info("Starting execution of findAllPatients");
		int id = 0;
		Response expectedValue = Response.status(Response.Status.ACCEPTED)
				.entity("No vitals found for patient ID: " + id).build();

		Service service = new Service();
		Response actualValue = service.findAllPatients(id);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
	@Test
	public void findAllPatientsOkStatus() throws Exception {
		log.info("Starting execution of findAllPatients");
		int id = 1;
		Response expectedValue = Response.status(Response.Status.OK)
				.entity("No vitals found for patient ID: " + id).build();

		Service service = new Service();
		Response actualValue = service.findAllPatients(id);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}

	@Test
	public void findByDateRangeFail() {
		log.info("Starting execution of findByDateRange");
		String fromDate = "";
		String toDate = "";
		Response expectedValue = Response.status(Response.Status.BAD_REQUEST)
				.entity("No data present for the date range: " + fromDate + " to " + toDate).build();

		Service service = new Service();
		Response actualValue = service.findByDateRange(fromDate, toDate);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}

	@Test
	public void findByDateRangePass() {
		log.info("Starting execution of findByDateRange");
		String fromDate = "2001-01-01";
		String toDate = "2011-01-01";
		Response expectedValue = Response.status(Response.Status.OK)
				.entity("No data present for the date range: " + fromDate + " to " + toDate).build();

		Service service = new Service();
		Response actualValue = service.findByDateRange(fromDate, toDate);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
	@Test
	public void findByDateRangeNoResult() {
		log.info("Starting execution of findByDateRange");
		String fromDate = "2011-01-01";
		String toDate = "2011-02-01";
		Response expectedValue = Response.status(Response.Status.ACCEPTED)
				.entity("No data present for the date range: " + fromDate + " to " + toDate).build();

		Service service = new Service();
		Response actualValue = service.findByDateRange(fromDate, toDate);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}

	@Test
	public void findByRecentlyStoredNoResult() {
		log.info("Starting execution of findByRecentlyStored");
		int id = 0;
		Response expectedValue = Response.status(Response.Status.ACCEPTED)
				.entity("No data present for the date range: " + id).build();
		Service service = new Service();
		Response actualValue = service.findByRecentlyStored(id);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
	@Test
	public void findByRecentlyStoredPass() {
		log.info("Starting execution of findByRecentlyStored");
		int id = 1;
		Response expectedValue = Response.status(Response.Status.OK)
				.entity("No data present for the date range: " + id).build();
		Service service = new Service();
		Response actualValue = service.findByRecentlyStored(id);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}

	@Test
	public void findByVitalIdNoResult() {
		log.info("Starting execution of findByVitalId");
		int patientId = 0;
		List<Integer> vitalIds = null;
		Response expectedValue = Response.status(Response.Status.ACCEPTED)
				.entity("No data present for the date range: " + patientId).build();

		Service service = new Service();
		Response actualValue = service.findByVitalId(patientId, vitalIds);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
//	@Test
//	public void findByVitalIdPass() {
//		log.info("Starting execution of findByVitalId");
//		int patientId = 1;
//		List<Integer> vitalIds = new ArrayList<>('1');
//		Response expectedValue = Response.status(Response.Status.OK)
//				.entity("No data present for the date range: " + patientId).build();
//
//		Service service = new Service();
//		Response actualValue = service.findByVitalId(patientId, vitalIds);
//		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
//		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
//	}

	@Test
	public void addPatientVitalsPass() {
			log.info("Starting execution of addPatientVitals");
			Vitals vitalDetails = new Vitals();
			addData(vitalDetails);

			Response expectedValue = Response.status(Response.Status.NOT_ACCEPTABLE)
					.entity("No data present for the date range: " + vitalDetails).build();
			Service service = new Service();
			Response actualValue = service.addPatientVitals(vitalDetails);
			log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
			Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
	@Test
	public void addPatientVitalsInvalidVitalID() {
		log.info("Starting execution of addPatientVitals");
		Vitals vitalDetails = new Vitals();
		addData(vitalDetails);
		vitalDetails.setVital_id(null);

		Response expectedValue = Response.status(Response.Status.BAD_REQUEST)
				.entity("No data present for the date range: " + vitalDetails).build();
		Service service = new Service();
		Response actualValue = service.addPatientVitals(vitalDetails);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
	@Test
	public void addPatientVitalsInvalidPatientID() {
		log.info("Starting execution of addPatientVitals");
		Vitals vitalDetails = new Vitals();
		addData(vitalDetails);
		vitalDetails.setPatient_id(null);

		Response expectedValue = Response.status(Response.Status.BAD_REQUEST)
				.entity("No data present for the date range: " + vitalDetails).build();
		Service service = new Service();
		Response actualValue = service.addPatientVitals(vitalDetails);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
	@Test
	public void addPatientVitalsInvalidName() {
		log.info("Starting execution of addPatientVitals");
		Vitals vitalDetails = new Vitals();
		addData(vitalDetails);
		vitalDetails.setName(null);

		Response expectedValue = Response.status(Response.Status.BAD_REQUEST)
				.entity("No data present for the date range: " + vitalDetails).build();
		Service service = new Service();
		Response actualValue = service.addPatientVitals(vitalDetails);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
	
	@Test
	public void addPatientVitalsInvalidDate() {
		log.info("Starting execution of addPatientVitals");
		Vitals vitalDetails = new Vitals();
		addData(vitalDetails);
		vitalDetails.setFirst_updated(null);

		Response expectedValue = Response.status(Response.Status.BAD_REQUEST)
				.entity("No data present for the date range: " + vitalDetails).build();
		Service service = new Service();
		Response actualValue = service.addPatientVitals(vitalDetails);
		log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
		Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}

	private void addData(Vitals vitalDetails) {
		Patient patient = new Patient();
		patient.setPatient_id("12");
		vitalDetails.setFirst_updated("2022-05-29");
		vitalDetails.setLast_updated("2022-06-29");
		vitalDetails.setMeasurement(345);
		vitalDetails.setName("ABC");
		vitalDetails.setPatient(patient);
		vitalDetails.setUnits("F");
		vitalDetails.setUpdateCount(12);
		vitalDetails.setVital_id("2233");
		vitalDetails.setPatient_id("1");
	
}

	@Test
	public void deletePatientVitals() {
			log.info("Starting execution of deletePatientVitals");
			int vitalId = 2233;
			Service service = new Service();

			Response actualValue = service.deletePatientVitals(vitalId);
			Assertions.assertTrue(!actualValue.getEntity().toString().isEmpty());
			
	}

	@Test
	public void updatePatientVitals() {
			log.info("Starting execution of updatePatientVitals");
			Vitals vitalDetails  = new Vitals();
			addData(vitalDetails);
			Service service = new Service();
			
			Response expectedValue = Response.status(Response.Status.BAD_REQUEST)
					.entity("Unable to delete using vital ID: ").build();
			Response actualValue = service.updatePatientVitals(vitalDetails);
			log.info("Expected Value=" + expectedValue + " . Actual Value=" + actualValue);
			Assertions.assertEquals(expectedValue.getStatus(), actualValue.getStatus());
	}
}

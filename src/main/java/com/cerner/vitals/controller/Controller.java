package com.cerner.vitals.controller;

import java.util.List;

import com.cerner.vitals.model.Patient;
import com.cerner.vitals.model.Vitals;
import com.cerner.vitals.service.Service;
import com.cerner.vitals.utils.UserValidationUtil;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;


@Path("app")
public class Controller {
	
	Service service = new Service();
	UserValidationUtil userUtil = new UserValidationUtil();
    
    @GET
    @Path("/getByPatientId")
    @RolesAllowed({"admin","non-admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vitals> findById(@QueryParam("id") int id) throws Exception{
		return service.findAllPatients(id);
    }
    
    @GET
    @Path("/getByDateRange")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vitals> findByDateRange(@QueryParam("from_date") String fromDate, @QueryParam("to_date") String toDate) throws Exception{
		return service.findByDateRange(fromDate,toDate);
    }
    
    @GET
    @Path("/getByRecentlyStored")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vitals> findByRecentlyStored(@QueryParam("patient_id") int id) throws Exception{
		return service.findByRecentlyStored(id);
    }
    
    @GET
    @Path("/getByVitalId")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public List<Vitals> findByRecentlyStored(@QueryParam("patient_id") int patientId, @QueryParam("vital_id") List<Integer> vitalIds) throws Exception{
		return service.findByVitalId(patientId, vitalIds);
    }
    
    @POST
    @Path("/addPatientVitals")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addPatientVitals(Patient details) throws Exception{
		return service.addPatientVitals(details);
    }
    
    @Path("/genToken")
	@POST
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public Response checkUserForToken(
			@FormParam("user")String user,
			@FormParam("pwd")String pwd
			) {
		return userUtil.isCredententialsValid(user, pwd);
		
	}
}

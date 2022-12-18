package com.cerner.vitals.controller;

import java.util.List;

import com.cerner.vitals.model.Vitals;
import com.cerner.vitals.service.Service;
import com.cerner.vitals.utils.UserValidationUtil;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
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
    public Response findById(@QueryParam("id") int id) throws Exception{
		return service.findAllPatients(id);
    }
    
    @GET
    @Path("/getByDateRange")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByDateRange(@QueryParam("from_date") String fromDate, @QueryParam("to_date") String toDate) throws Exception{
		return service.findByDateRange(fromDate,toDate);
    }
    
    @GET
    @Path("/getByRecentlyStored")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByRecentlyStored(@QueryParam("patient_id") int id) throws Exception{
		return service.findByRecentlyStored(id);
    }
    
    @GET
    @Path("/getByVitalId")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByRecentlyStored(@QueryParam("patient_id") int patientId, @QueryParam("vital_id") List<Integer> vitalIds) throws Exception{
		return service.findByVitalId(patientId, vitalIds);
    }
    
    @POST
    @Path("/addVitalId")
    @RolesAllowed({"admin","non-admin"})
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPatientVitals(Vitals vitalDetails) throws Exception{
    	Response rsp = service.addPatientVitals(vitalDetails);
		return rsp;
    }
    
    @DELETE
    @Path("/deletePatientVitals")
    @RolesAllowed("admin")
    public Response deletePatientVitals(@QueryParam("vital_id") int vitalId) throws Exception{
		return service.deletePatientVitals(vitalId);
    }
    
    @PUT
    @Path("/updateVitals")
    @RolesAllowed("admin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePatientVitals(Vitals vitalDetails) throws Exception{
		return service.updatePatientVitals(vitalDetails);
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

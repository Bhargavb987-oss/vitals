package com.cerner.vitals.config;


import org.glassfish.jersey.server.ResourceConfig;

import com.cerner.vitals.filter.SecurityFilter;

import jakarta.ws.rs.ApplicationPath;


@ApplicationPath("/*")
public class AppConfig extends ResourceConfig{
	public AppConfig() {
		packages("com.cerner.vitals");
		register(SecurityFilter.class);
	}

}
package com.cerner.vitals.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {
	
	@Override
	public String toString() {
		return "Patient [patient_id=" + patient_id + ", name=" + name + ", gender=" + gender + ", birth_date="
				+ birth_date + ", weight=" + weight + ", height=" + height + ", active=" + active + ", patient_vitals="
				+ patient_vitals + "]";
	}
	@Id
	private String patient_id;
	private String name;
	private String gender;
	private String birth_date;
	private int weight;
	private int height;
	private boolean active;
	
	@OneToMany(targetEntity = Vitals.class, 
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	@JoinColumn(referencedColumnName = "patient_id")
	private List<Vitals> patient_vitals = new ArrayList<>();
		
	public List<Vitals> getPatient_vitals() {
		return patient_vitals;
	}
	public void setPatient_vitals(List<Vitals> patient_vitals) {
		this.patient_vitals = patient_vitals;
	}
	
	public String getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(String patient_id) {
		this.patient_id = patient_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String getBirth_date() {
		return birth_date;
	}
	public void setBirth_date(String birth_date) {
		this.birth_date = birth_date;
	}

	
	public Patient(String patient_id, String name, String gender, String birth_date, int weight, int height,
			boolean active, List<Vitals> patient_vitals) {
		super();
		this.patient_id = patient_id;
		this.name = name;
		this.gender = gender;
		this.birth_date = birth_date;
		this.weight = weight;
		this.height = height;
		this.active = active;
		this.patient_vitals = patient_vitals;
	}
	public Patient() {
	}

}

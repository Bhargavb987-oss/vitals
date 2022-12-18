package com.cerner.vitals.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Vitals")
public class Vitals {

	@Id
	private String vital_id;
	private String patient_id;
	private String name;
	private String last_updated;
	private String first_updated;
	private int measurement;
	private int updateCount;
	private String units;
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "patient",referencedColumnName = "patient_id")
	private Patient patient;
	
	
	public String getVital_id() {
		return vital_id;
	}
	public void setVital_id(String vital_id) {
		this.vital_id = vital_id;
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
	public String getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(String last_updated) {
		this.last_updated = last_updated;
	}
	public String getFirst_updated() {
		return first_updated;
	}
	public void setFirst_updated(String first_updated) {
		this.first_updated = first_updated;
	}
	public int getMeasurement() {
		return measurement;
	}
	public void setMeasurement(int measurement) {
		this.measurement = measurement;
	}
	public int getUpdateCount() {
		return updateCount;
	}
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	
	@Override
	public String toString() {
		return "Vitals [vital_id=" + vital_id + ", patient_id=" + patient_id + ", name=" + name + ", lastUpdated="
				+ last_updated + ", first_updated=" + first_updated + ", measurement=" + measurement + ", updateCount="
				+ updateCount + ", units=" + units + "]";
	}
	public Patient getPatient() {
		return patient;
	}
	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
}


package com.vinfotech.model;

import java.util.List;

public class PatientCategory {
	public String name;
	private List<Patient> patientList;

	public PatientCategory(String name, List<Patient> patientList) {
		super();
		this.name = name;
		this.patientList = patientList;
	}

	public List<Patient> getPatients() {
		return this.patientList;
	}

	public void setPatients(List<Patient> list) {
		this.patientList = list;
	}
}

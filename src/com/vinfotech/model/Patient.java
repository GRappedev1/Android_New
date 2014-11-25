package com.vinfotech.model;

public class Patient {
	public String image;
	public String name;
	public int age;
	public String gender;
	public String admittedIn;
	public String cause;

	public Patient(String image, String name, int age, String gender, String admittedIn, String cause) {
		super();
		this.image = image;
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.admittedIn = admittedIn;
		this.cause = cause;
	}

}

package com.team6;

public class Course {
public String period;
public String courseNumber;
public String teacher;
public String room; // room is needed for on-call sheets/ printable version

	public Course(String teacher, String period, String courseNumber) { // Implement room number !
		this.period = period;
		this.courseNumber = courseNumber;
		this.teacher = teacher;
	}
	
	public String toString() {
		return period + ": " + courseNumber;
	}

}

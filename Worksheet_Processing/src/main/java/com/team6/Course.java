package com.team6;

public class Course {
public int period;
public String courseNumber;
public String room; // room is needed for on-call sheets/ printable version

public Course(int period, String courseNumber) { // Implement room number !
	this.period = period;
	this.courseNumber = courseNumber;
}

public int getPeriod() {
	return period;
}

public String getCourseNumber() {
	return courseNumber;
}


}
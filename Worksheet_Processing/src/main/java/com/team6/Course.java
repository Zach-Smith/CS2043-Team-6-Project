package com.team6;

public class Course {
private String period;
private String courseNumber;
public final String teacher;
public String room; // room is needed for on-call sheets/ printable version

	public Course(String teacher, String period, String courseNumber) { // Implement room number !
		this.period = period;
		this.courseNumber = courseNumber;
		this.teacher = teacher;
	}
	
	public static int getPeriodIndex(String period) {
		if (period.equals("Period 1")) {
			return 0;
		}
		else if (period.equals("Period 2")) {
			return 1;
		}
		else if (period.equals("Period 3A")) {
			return 2;
		}
		else if (period.equals("Period 3B")) {
			return 3;
		}
		else if (period.equals("Period 4")) {
			return 4;
		}
		else {
			return -1;
		}
	}
	
	public String toString() {
		return "Teacher: " + teacher + ", Period: " + period + ", Course: " + courseNumber;
	}
	
	public String getCourseNumber() {
		return courseNumber;
	}
	
	public String getPeriod() {
		return period;
	}

}

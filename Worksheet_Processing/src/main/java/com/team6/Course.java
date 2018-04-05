package com.team6;

public class Course {
private String period;
private String courseNumber;
public final String teacher;
public String room; // room is needed for on-call sheets/ printable version

	public Course(String teacher, String period, String courseNumber,String room) { // Implement room number !
		this.period = period;
		this.courseNumber = courseNumber;
		this.teacher = teacher;
		this.room = room;
	}
	
	public static int getPeriodIndex(String period) {
		if (period.equals("Period 1")) {
			return 0;
		}
		else if (period.equals("Period 2")) {
			return 1;
		}
		else if (period.equals("Period 3a")) {
			return 2;
		}
		else if (period.equals("Period 3b")) {
			return 3;
		}
		else if (period.equals("Period 4")) {
			return 4;
		}
		else {
			return -1;
		}
	}
	
	public static String getIndexFromPeriod(int periodIndex) {
		if(periodIndex == 0)
			return "Period 1";
		else if(periodIndex == 1)
			return "Period 2";
		else if(periodIndex == 2)
			return "Period 3A";
		else if(periodIndex == 3)
			return "Period 3B";
		else if(periodIndex == 4)
			return "Period 4";
		else
			return "";
}
	
	
	public String toString() {
		return "Teacher: " + teacher + ", Period: " + period + ", Course: " + courseNumber + ", Room: " + room ;
	}
	
	public String getCourseNumber() {
		return courseNumber;
	}
	
	public String getPeriod() {
		return period;
	}

	public String getRoom(){
			return room;
	}
}

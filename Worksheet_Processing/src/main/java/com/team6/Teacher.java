package com.team6;

import java.util.ArrayList;

public class Teacher {
	public final String initials;
	public ArrayList<Course> schedule;
	private int onCallsTotal, onCallsWeekly;
	private String skill;
	
	public Teacher(String initialsIn, ArrayList<Course> scheduleIn) {
		initials = initialsIn;
		schedule = scheduleIn;
		onCallsWeekly = 0;
		onCallsTotal = 0;
	}
	
	public void setSkill(String skillIn) {
		skill = skillIn;		
	}
	
	public void setTotalOnCalls(int num) {
		onCallsTotal = num;
	}
	
	public String toString(){
		String s = "Teacher: " + initials + "\n";
		for (int i = 0; i < 5; i++) {
			s += schedule.get(i) + "\n";
		}
		s += "Total On Calls: " + onCallsTotal + "\n";
		
		return s;
		
	}

}

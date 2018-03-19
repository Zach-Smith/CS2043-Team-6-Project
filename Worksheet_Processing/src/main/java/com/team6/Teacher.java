package com.team6;

import java.util.ArrayList;

public class Teacher {

	public String initials;
	public ArrayList<Course> schedule;
	public int onCallsTotal;
	public int onCallsWeekly;
	public String skill;
	
	public Teacher(String initialsIn, ArrayList<Course> scheduleIn) {
		initials = initialsIn;
		schedule = scheduleIn;
		onCallsWeekly = 0;
		onCallsTotal = 0;
	}
	
	public Teacher(String initialsIn, int weeklyOnCall, int totalOnCall) {
		initials = initialsIn;
		schedule = null;
		onCallsWeekly = weeklyOnCall;
		onCallsTotal = totalOnCall;
	}
	
	public Teacher(String initialsIn) {
		initials = initialsIn;
		onCallsTotal = 0;
	}
	
	public String getInitials() {
		return initials;
	}
	
	public void setSkill(String skillIn) {
		skill = skillIn;		
	}
	
	public void setTotalOnCalls(int num) {
		onCallsTotal = num;
	}
	
	public void setOnCallsWeek(int num) {
		onCallsWeekly = num;
	}
	
	public void increaseTotalOnCalls(int num) {
		onCallsTotal += num;
	}
	
	public void increaseWeeklyOnCalls(int num) {
		onCallsWeekly += num;
	}
	
	public String toString(){
		String s = "Teacher: " + initials + "\n";
		for (int i = 0; i < 5; i++) {
			//s += schedule.get(i) + "\n";
		}
		s += "Total On Calls: " + onCallsTotal + "\n";
		
		return s;
		
	}

}

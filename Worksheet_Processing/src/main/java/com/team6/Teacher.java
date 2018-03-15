package com.team6;

import java.util.ArrayList;

public class Teacher {
	private String initials;
	private ArrayList<String> schedule;
	private int onCallsTotal;
	private int onCallsWeek;
	private String skill;
	private final String[] periods = {"1","2","3A","3B","4"};
	
	public Teacher(String initialsIn, ArrayList<String> scheduleIn) {
		initials = initialsIn;
		schedule = scheduleIn;
		onCallsTotal = 0;
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
		onCallsWeek = num;
	}
	
	public String toString(){
		String s = "Teacher: " + initials + "\n";
		/*
		for (int i = 0; i < 5; i++) {
			s += "Period " + periods[i] + ": " + schedule.get(i) + "\n";
		}*/
		s += "Total On Calls: " + onCallsTotal + "\n";
		s += "Weekly On Calls: " + onCallsWeek + "\n";
		
		return s;
		
	}

}

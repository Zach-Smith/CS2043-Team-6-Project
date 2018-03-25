package com.team6;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public class ScheduleProcessor {
	ArrayList<ArrayList<String>> arr;
	ScheduleReader sr;
	
	public ScheduleProcessor() throws InvalidFormatException, IOException {
		  sr = new ScheduleReader();
		  arr = sr.convertSpreadsheet();
	}
	  
	  
	public ArrayList<Teacher> generateTeachers(){
		ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		  
		for (int i = 1; i < arr.size(); i++) {
			String initials = arr.get(i).get(0);
			ArrayList<Course> schedule = new ArrayList<Course>();
			for (int j = 1; j < 6; j++) {
			 schedule.add(new Course(initials,arr.get(0).get(j),arr.get(i).get(j)));
			}
			teachers.add(new Teacher(initials,schedule));		  
		}
		  
		return teachers;  
	}
		
	public boolean checkScheduleFormat() {
		return sr.checkScheduleFormat();
	}
}
package com.team6;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;



public class AbsencesProcessor {
	AbsencesReader ar;
	ArrayList<ArrayList<String>> arr;
	 
	 public AbsencesProcessor(int week) throws InvalidFormatException, IOException {
		   ar = new AbsencesReader(week);
		   arr = ar.readAbsences(); 
	  }
	 
	 
	 	
	 public ArrayList<ArrayList<Course>> findAbsences(ArrayList<Teacher> teachers){
		 ArrayList<Course> courses = new ArrayList<Course>();
		 ArrayList<ArrayList<Course>> weekly = new ArrayList<ArrayList<Course>>();
		 int indexJ = 2;
		 while(weekly.size() < 5) {
			 
			 
			 for(int i = 3; i < arr.size()-1 && !arr.get(i).get(0).equals("a"); i++) {
				 
				 String initials = arr.get(i).get(0);
				 int index;
				 for(index = 0;index < teachers.size(); index++) {
					 if(initials.equals(teachers.get(index).getInitials()))
						 break;
				  }	
				 int scheduleIndex = 0;
				 for(int j = indexJ; j < indexJ+5; j++) {
					 if(arr.get(i).get(j).equals("a")) {
						 courses.add(teachers.get(index).getSchedule().get(scheduleIndex));
					 }	
					 scheduleIndex++;
					 if(scheduleIndex == 5) {
						 scheduleIndex = 0;
					 }
				 }
			 }
			 indexJ = indexJ+5;
			 weekly.add(courses);
			 courses = new ArrayList<Course>();
		 }
		 return weekly;
	 }
}


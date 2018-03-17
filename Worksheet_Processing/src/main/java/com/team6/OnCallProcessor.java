package com.team6;
import java.util.Random;
import java.util.ArrayList;

public class OnCallProcessor {
	public final int MAX_TOTAL_ON_CALLS;
	private ArrayList<Teacher> teachers;
	private ArrayList<OnCall> onCallList;
	private ArrayList<Course> absenteeList;
	private int month;
	private int day;
	
	public OnCallProcessor(ArrayList<Teacher> teachersIn,ArrayList<Course> absenteeListIn, int maxOnCallsIn, int monthIn, int dayIn){
		MAX_TOTAL_ON_CALLS = maxOnCallsIn;
		teachers = teachersIn;
		onCallList = new ArrayList<OnCall>();
		absenteeList = absenteeListIn;
		month = monthIn;
		day = dayIn;
		
		
	}
	
	
	
	
	public void updateOnCallTally(String teacherName,int month,int day,String period) {
		//To be implemented
		
	
	}
	
	public int findMinOnCalls(ArrayList<Teacher> list) {
		int min = list.get(0).onCallsTotal;
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).onCallsTotal < min) {
				min = list.get(i).onCallsTotal;
			}
		}
		
		return min;
	}
	
	public Teacher getAbsentTeacher(Course course) {
		for (int i = 0; i < teachers.size(); i++) {
			if (course.teacher.equals(teachers.get(i).initials)) {
				return teachers.get(i);
			}	
				
		}
		return null;
	}	
	
	public void removeAbsentTeachers(ArrayList<Teacher> list,String period) {
		
		for (int i = 0; i < absenteeList.size(); i++){
			for (int j = 0; j < list.size(); j++) {
				if (absenteeList.get(i).teacher.equals(list.get(j).initials) && absenteeList.get(i).period.equals(period)) {
						list.remove(j);
					
				}
			}
			
		}
	}
	
	
	public void removeTeachingTeachers(ArrayList<Teacher> list,String period) {
		int periodIndex = Course.getPeriodIndex(period);
		if (periodIndex >= 0) {
			for (int i = 0; i < teachers.size(); i++){
				for (int j = 0; j < list.size(); j++) {
					if (teachers.get(i).initials.equals(list.get(j).initials) && !(teachers.get(i).schedule.get(periodIndex).period.equals(""))) {
							list.remove(j);
					
					}
				}
			
			}
		}	
	}
	
	public void removeOnCallTeachers(ArrayList<Teacher> list,String period) {
		for (int i = 0; i < onCallList.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				
				if (onCallList.get(i).onCaller.initials.equals(list.get(j).initials)){
					list.remove(j);
				}
			}
		}
	}
	
	public boolean generateOnCallList() {
		for (int i = 0;i < absenteeList.size(); i++) {
			if(!assignOnCaller(absenteeList.get(i))) {
				//if(!assignSupply(absenteeList)){
					return false;
				//}
				
			}
			
		}
		
		return true;
		
	}
	
	/*
	public boolean assignSupply(Course absenteeCourse) {
		Teacher absentTeacher = this.getAbsentTeacher(absenteeCourse);
		
		
	}
	*/
																	
	public boolean assignOnCaller(Course absenteeCourse) {
		
		Teacher absentTeacher = this.getAbsentTeacher(absenteeCourse);
		Random rand = new Random();
		ArrayList<Teacher> list = teachers;
		
		//find period of absence
		String periodOfAbsence = absenteeCourse.period;
		
				
		//Remove teachers from list that are already occupied. They can't be assigned as on call. 
		this.removeAbsentTeachers(list,periodOfAbsence);
		this.removeTeachingTeachers(list,periodOfAbsence);
		this.removeOnCallTeachers(list,periodOfAbsence);
					
		
		int minOnCalls = this.findMinOnCalls(list);
		ArrayList<Teacher> minOnCallsTeachers = new ArrayList<Teacher>();
		
		//Go through teachers starting from those that have minimum total on calls 
		//and 0 weekly on calls. If such teachers exit, make a list of them, randomly 
		//choose one from the list, and assign them as on call. If none exist, look 
		//for teachers with a higher number of total on calls and weekly on calls
		//If a teacher to assign as on call is found, update on call tally and on call list and return true
		//If empty list or arrived at max on calls, return false (need a supply teacher).
		for (int i = minOnCalls; i < MAX_TOTAL_ON_CALLS; i++) {
			for (int weeklyOnCallsIndex = 0; weeklyOnCallsIndex <= i; weeklyOnCallsIndex++){
				for (int listIndex = 0; listIndex < list.size(); listIndex++) {
					if (list.get(listIndex).onCallsTotal == i && list.get(listIndex).onCallsWeekly == weeklyOnCallsIndex) {
							minOnCallsTeachers.add(list.get(listIndex));
						
						
					}
					
					
				}
				
				if (!(minOnCallsTeachers.isEmpty())) {
					int chosen = rand.nextInt(minOnCallsTeachers.size());
					updateOnCallTally(minOnCallsTeachers.get(chosen).initials,month,day,periodOfAbsence);
					onCallList.add(new OnCall(minOnCallsTeachers.get(chosen),absenteeCourse,absentTeacher));
					updateTeachers(minOnCallsTeachers.get(chosen).initials);					
										
					
					return true;
				}	
			}
			
		}
		
		return false;	
		
	}
	
	public void updateTeachers(String initials){
		for (int i = 0; i < teachers.size(); i++) {
			Teacher t = teachers.get(i);
			if (t.initials.equals(initials)) {
				t.increaseTotalOnCalls(1);
				t.increaseWeeklyOnCalls(1);
			}
		}
		
	}
	
}

package com.team6;
import java.util.Random;
import java.util.ArrayList;

public class OnCallProcessor {
	public final int MAX_TOTAL_ON_CALLS;
	private ArrayList<Teacher> teachers;
	private ArrayList<SupplyTeacher> supplies;
	private ArrayList<OnCall> onCallList;
	private ArrayList<Course> absenteeList;
	
	
	public OnCallProcessor(ArrayList<Teacher> teachersIn,ArrayList<SupplyTeacher> suppliesIn, ArrayList<Course> absenteeListIn, int maxOnCallsIn){
		MAX_TOTAL_ON_CALLS = maxOnCallsIn;
		teachers = teachersIn;
		supplies = suppliesIn;
		onCallList = new ArrayList<OnCall>();
		absenteeList = absenteeListIn;
	}
	
		
	public int findMinOnCalls(ArrayList<Teacher> list) {
		int min = list.get(0).getTotalOnCalls();
		for (int i = 1; i < list.size(); i++) {
			if (list.get(i).getTotalOnCalls() < min) {
				min = list.get(i).getTotalOnCalls();
			}
		}
		
		return min;
	}
	
	public Teacher getAbsentTeacher(Course course) {
		for (int i = 0; i < teachers.size(); i++) {
			if (course.teacher.equals(teachers.get(i).getInitials())) {
				return teachers.get(i);
			}	
				
		}
		return null;
	}	
	
	public void removeAbsentTeachers(ArrayList<Teacher> list,String period) {
		
		for (int i = 0; i < absenteeList.size(); i++){
			for (int j = 0; j < list.size(); j++) {
				if (absenteeList.get(i).teacher.equals(list.get(j).getInitials()) && absenteeList.get(i).getPeriod().equals(period)) {
						
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
					if (teachers.get(i).getInitials().equals(list.get(j).getInitials()) && !(teachers.get(i).getSchedule().get(periodIndex).getCourseNumber().equals("S")) && !(teachers.get(i).getSchedule().get(periodIndex).getCourseNumber().equals("L"))) {
							
						list.remove(j);
					
					}
				}
			
			}
		}	
	}
	
	public void removeOnCallTeachers(ArrayList<Teacher> list,String period) {
		for (int i = 0; i < onCallList.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				
				if (onCallList.get(i).isFullTime() && onCallList.get(i).getOnCaller().getInitials().equals(list.get(j).getInitials())){
					list.remove(j);
				}
			}
		}
	}
	
	public boolean generateOnCallList() {
		for (int i = 0;i < absenteeList.size(); i++) {
			//Try to assign another teacher
			if(!assignOnCaller(absenteeList.get(i))) {
				//Try to assign supply
				if(!assignSupply(absenteeList.get(i))){
					return false;
				}
				
			}
			
		}
		
		return true;
		
	}
	
	public ArrayList<OnCall> getOnCallList(){
		return onCallList;
	}
	
	
	public boolean assignSupply(Course absenteeCourse) {
		Teacher absentTeacher = this.getAbsentTeacher(absenteeCourse);
		String periodOfAbsence = absenteeCourse.getPeriod();
		Random rand = new Random();
		
		//Construct list from list of supplies
		ArrayList<SupplyTeacher> list = this.CloneSupplies();
		
		//Remove supplies that are already teaching during that period
		this.removeOccupiedSupplies(list,periodOfAbsence);
	
		//Choose randomly from remaining supplies	
		if (!list.isEmpty()){
			int chosen = rand.nextInt(list.size());
			onCallList.add(new OnCall(supplies.get(chosen),absenteeCourse,absentTeacher));
			return true;
		}
		return false;
	}
	
	public void removeOccupiedSupplies(ArrayList<SupplyTeacher> list,String period) {
		for (int i = 0; i < list.size(); i++) {
			
			for (int j = 0; j < onCallList.size(); j++){
					
				if (!onCallList.get(j).isFullTime() && list.get(i).getName().equals(onCallList.get(j).getSupply().getName()) && onCallList.get(j).getCourse().getPeriod().equals(period)) { 
					list.remove(i);
					i--;
					break;
				}
			}
			
			
			
		}
	}	
	
	
	
	
	public ArrayList<SupplyTeacher> CloneSupplies(){
		ArrayList<SupplyTeacher> list = new ArrayList<SupplyTeacher>();
		for(int i = 0; i < supplies.size(); i++) {
			list.add(supplies.get(i));
		}
		
		return list;
	}
	
	public ArrayList<Teacher> CloneTeachers(){
		ArrayList<Teacher> list = new ArrayList<Teacher>();
		for(int i = 0; i < teachers.size(); i++) {
			list.add(teachers.get(i));
		}
		
		return list;
	}
																	
	public boolean assignOnCaller(Course absenteeCourse) {
		
		
		//If absenteeCourse is lunch or spare, no need to cover absence 
		if (absenteeCourse.getCourseNumber().equals("S") || absenteeCourse.getCourseNumber().equals("L")) {
			return true;
		}
		
		Teacher absentTeacher = this.getAbsentTeacher(absenteeCourse);
		Random rand = new Random();
		
		
		//Initialize list to be all full time teachers
		ArrayList<Teacher> list = this.CloneTeachers();
		
			
		//find period of absence
		String periodOfAbsence = absenteeCourse.getPeriod();
		
				
		//Remove teachers from list that are already occupied. They can't be assigned as on call. 
		this.removeAbsentTeachers(list,periodOfAbsence);
		this.removeTeachingTeachers(list,periodOfAbsence);
		this.removeOnCallTeachers(list,periodOfAbsence);
		
		
		
		if(list.size() > 0) {
		
			int minOnCalls = this.findMinOnCalls(list);
			
			ArrayList<Teacher> minOnCallsTeachers = new ArrayList<Teacher>();
		
			/*
			Go through teachers starting from those that have minimum total on calls 
			and 0 monthly on calls and 0 weekly on calls. If such teachers exist, 
			make a list of them, randomly 
			choose one from the list, and assign them as on call. If none exist, look 
			for teachers with a higher number of total on calls and monthly on calls and weekly on calls
			If a teacher to assign as on call is found, update on call list and return true
			If empty list or arrived at max on calls, return false (need a supply teacher).
			*/
			for (int i = minOnCalls; i < MAX_TOTAL_ON_CALLS; i++) {
				for (int monthlyOnCallsIndex = 0; monthlyOnCallsIndex <= i; monthlyOnCallsIndex++){
					for (int weeklyOnCallsIndex = 0; weeklyOnCallsIndex <= i; weeklyOnCallsIndex++){
						for (int listIndex = 0; listIndex < list.size(); listIndex++) {
							if (list.get(listIndex).getTotalOnCalls() == i && list.get(listIndex).getMonthlyOnCalls() == monthlyOnCallsIndex && list.get(listIndex).getWeeklyOnCalls() == weeklyOnCallsIndex) {
								minOnCallsTeachers.add(list.get(listIndex));
							}
						
						}
						
						if (!(minOnCallsTeachers.isEmpty())) {
							int chosen = rand.nextInt(minOnCallsTeachers.size());
							onCallList.add(new OnCall(minOnCallsTeachers.get(chosen),absenteeCourse,absentTeacher));
							updateTeachers(minOnCallsTeachers.get(chosen).getInitials());					
											
						
							return true;
						}
						
															
					}
				
				}
		
			}
		}
		
		
		return false;	
		
	}
	
	public void updateTeachers(String initials){
		for (int i = 0; i < teachers.size(); i++) {
			Teacher t = teachers.get(i);
			if (t.getInitials().equals(initials)) {
				t.increaseTotalOnCalls(1);
				t.increaseWeeklyOnCalls(1);
				t.increaseMonthlyOnCalls(1);
			}
		}
		
	}
	
	public void sortOnCalls() {
		int min;
		OnCall temp;
		
		for (int i = 0; i < onCallList.size() - 1; i++) {
			min = i;
			for (int j = i + 1; j < onCallList.size(); j++) {
				if (onCallList.get(j).compareTo(onCallList.get(min)) < 0){
					min = j;
				}
			}
			
			//Swap
			temp = onCallList.get(min);
			onCallList.remove(min);
			onCallList.add(min,onCallList.get(i));
			onCallList.remove(i);
			onCallList.add(i,temp);
		}
		
		
		
	}
	
	
	public String toString() {
		this.sortOnCalls();
		String s = "";
		int i = 0;
		
		s += "Period 1\n";
		s+= "------------\n\n";
		
		while(i < onCallList.size() && onCallList.get(i).getCourse().getPeriod().equals("Period 1")) {
					
			s += onCallList.get(i) + "\n\n";
			i++;
		}
		
		s += "Period 2\n";
		s+= "------------\n\n";
		
		while(i < onCallList.size() && onCallList.get(i).getCourse().getPeriod().equals("Period 2")) {
					
			s += onCallList.get(i) + "\n\n";
			i++;
		}
		
		s += "Period 3A\n";
		s+= "------------\n\n";
		
		while(i < onCallList.size() && onCallList.get(i).getCourse().getPeriod().equals("Period 3A")) {
					
			s += onCallList.get(i) + "\n\n";
			i++;
		}
		
		s += "Period 3B\n";
		s+= "------------\n\n";
		
		while(i < onCallList.size() && onCallList.get(i).getCourse().getPeriod().equals("Period 3B")) {
					
			s += onCallList.get(i) + "\n\n";
			i++;
		}
		
		s += "Period 4\n";
		s+= "------------\n\n";
		
		while(i < onCallList.size() && onCallList.get(i).getCourse().getPeriod().equals("Period 4")) {
					
			s += onCallList.get(i) + "\n\n";
			i++;
		}
		
		return s;
		
	}
	
}

import com.team6.*;

import java.util.*;

public class Application {
	public static void main(String args[]) {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			System.out.println(ReadWorkBook.getSheetByMonth());
			ArrayList<ArrayList<String>> onCallTally = ReadWorkBook.readOnCallTally(ReadWorkBook.getSheetByMonth());
			System.out.println(onCallTally.get(2).get(2));
			//System.out.println(onCallTally.get(7).toString());
			
			ArrayList<Teacher> onCallTeachers = ReadWorkBook.generateTeachersFromOnCall(onCallTally);
			//System.out.println(onCallTeachers.size());
			//System.out.println(onCallTeachers.get(1).toString());
			ArrayList<Teacher> teachers = new ArrayList<Teacher>();
			
			teachers.add(new Teacher("KST", 2, 5));
			teachers.add(new Teacher("WWE", 0, 2));
			teachers.add(new Teacher("MIMI", 1, 1));
			teachers.add(new Teacher("TEA", 1, 1));
			teachers.add(new Teacher("CS", 2, 3));
			teachers.add(new Teacher("ME", 2, 2));
			teachers.add(new Teacher("SJD", 1, 2));
			ArrayList<Course> courses = new ArrayList<Course>(); 
			courses.add(new Course(1,"Math"));
			courses.add(new Course(4,"English"));
			courses.add(new Course(2,"Sport"));
			courses.add(new Course(5,"Sport"));
			courses.add(new Course(2,"Music"));
			ArrayList<OnCall> onCalls = new ArrayList<OnCall>(); 
			onCalls.add(new OnCall(teachers.get(0),courses.get(0),new Teacher("MISS", 1, 2)));
			onCalls.add(new OnCall(teachers.get(1),courses.get(1),new Teacher("MISS", 1, 2)));
			onCalls.add(new OnCall(teachers.get(2),courses.get(2),new Teacher("MISS", 1, 2)));
			onCalls.add(new OnCall(teachers.get(3),courses.get(3),new Teacher("MISS", 1, 2)));
			onCalls.add(new OnCall(teachers.get(4),courses.get(4),new Teacher("MISS", 1, 2)));
			
			for (int i=0; i<=onCallTeachers.size()-1; i++)
				System.out.println(onCallTeachers.get(i).toString());
			//DailyRecord record = new DailyRecord(onCalls);
			
			for (int i=0; i<=onCalls.size()-1; i++)
				System.out.println(onCalls.get(i).toString());
			
			
		//	System.out.println(onCallTeachers.get(0).toString());
			  
			  
			  
			  
		/*
			if(sp.checkScheduleFormat()) {
				System.out.println("Schedule has correct headers");
			}
			else {
				System.out.println("Schedule is NOT in correct format. Please check headers");			
			}
			
			System.out.println();
			
			ArrayList<Teacher> teachers = sp.generateTeachers();
			
			for (int i = 0; i < teachers.size(); i++) {
				System.out.println(teachers.get(i));
			}
		*/	
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

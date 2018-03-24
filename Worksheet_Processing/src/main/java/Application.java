import com.team6.*;

import java.util.*;

public class Application {
	public static void main(String args[]) {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			ArrayList<ArrayList<String>> onCallTally = ReadOnCallTally.readOnCallTally(ReadOnCallTally.getSheetByMonth());
			
			//System.out.println(onCallTeachers.size());
			//System.out.println(onCallTeachers.get(1).toString());
			ArrayList<Teacher> teachers = new ArrayList<Teacher>();
			
			teachers.add(new Teacher("AC"));
			teachers.add(new Teacher("LC"));
			teachers.add(new Teacher("FG"));
			teachers.add(new Teacher("MJ"));
			teachers = ReadOnCallTally.updateTeachersFromOnCall(onCallTally, teachers);
			ArrayList<Course> courses = new ArrayList<Course>(); 
			courses.add(new Course(1,"Math"));
			courses.add(new Course(4,"English"));
			courses.add(new Course(2,"Sport"));
			courses.add(new Course(5,"Sport"));
			courses.add(new Course(2,"Music"));
			ArrayList<OnCall> onCalls = new ArrayList<OnCall>(); 
			onCalls.add(new OnCall(teachers.get(0),courses.get(0),new Teacher("MISS", 1, 2)));
			onCalls.add(new OnCall(teachers.get(1),courses.get(1),new Teacher("MISS", 1, 2)));
			//onCalls.add(new OnCall(teachers.get(2),courses.get(2),new Teacher("MISS", 1, 2)));
			//onCalls.add(new OnCall(teachers.get(3),courses.get(3),new Teacher("MISS", 1, 2)));
			//onCalls.add(new OnCall(teachers.get(4),courses.get(4),new Teacher("MISS", 1, 2)));
			
			for (int i=0; i<=teachers.size()-1; i++)
				System.out.println(teachers.get(i).toString());
			//DailyRecord record = new DailyRecord(onCalls);
			
			//for (int i=0; i<=onCalls.size()-1; i++)
				//System.out.println(onCalls.get(i).toString());
			
			
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

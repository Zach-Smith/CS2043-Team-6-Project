import com.team6.*;

import java.util.*;

public class Application {
	public static void main(String args[]) {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			ArrayList<ArrayList<String>> onCallTally = ReadWorkBook.readOnCallTally();
			//System.out.println(onCallTally.get(7).toString());
			ArrayList<Teacher> onCallTeachers = ReadWorkBook.generateTeachersFromOnCall(onCallTally);
			System.out.println(onCallTeachers.size());
			System.out.println(onCallTeachers.get(1).toString());

			for (int i=0; i<=onCallTeachers.size()-1; i++)
				System.out.println(onCallTeachers.get(i).toString());
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

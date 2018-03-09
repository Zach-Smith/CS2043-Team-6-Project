import com.team6.*;
import java.util.ArrayList;

public class Application {
	public static void main(String args[]) {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			ArrayList<ArrayList<String>> onCallTally = ReadWorkBook.readOnCallTally();
			ArrayList<Teacher> onCallTeachers = ReadWorkBook.generateTeachersFromOnCall(onCallTally);
			/*
			System.out.println("On-call Tally:"); 
			  for(int i = 0; i < onCallTally.size(); i++) {
				  for(int j = 0; j < onCallTally.get(i).size(); j++) {
					  System.out.print(onCallTally.get(i).get(j)+ '\t');
				  }
				  System.out.println();
			  }
			  */
			for (int i=0; i<=onCallTeachers.size()-1; i++)
				System.out.println(onCallTeachers.get(i).toString());
			System.out.println(onCallTeachers.get(0).toString());
			  
			  
			  
			  
			  
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
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

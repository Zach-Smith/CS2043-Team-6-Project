import com.team6.*;
import java.util.ArrayList;

public class Application {
	public static void main(String args[]) {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			AbsencesProcessor ap = new AbsencesProcessor(1);
			
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
			
			ArrayList<Course> courses = ap.findAbsences(teachers);
			
			
			System.out.println("Courses that need to be covered");
			for(int i = 0; i < courses.size(); i++) {
				System.out.println(courses.get(i).teacher + ": " + courses.get(i));
			}
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

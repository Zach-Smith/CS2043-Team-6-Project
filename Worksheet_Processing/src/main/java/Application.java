import com.team6.*;
import java.util.ArrayList;

public class Application {
	public static void main(String args[]) {
		
		int month = 1; //January
		int dayOfWeek = 0; //Monday
		int week = 1; //first week
		int max_on_calls = 4;
				
		try {
			
			//Test ScheduleProcessor
			
			ScheduleProcessor sp = new ScheduleProcessor();

			if(sp.checkScheduleFormat()) {
				System.out.println("Schedule has correct headers");
				
				System.out.println();
				
				ArrayList<Teacher> teachers = sp.generateTeachers();
				
				for (int i = 0; i < teachers.size(); i++) {
					System.out.println(teachers.get(i));
				}
				
				//Test AbsencesProcessor
				
				AbsencesProcessor ap = new AbsencesProcessor(week);
				ArrayList<ArrayList<Course>> absenteeCoursesOfWeek = ap.findAbsences(teachers);
				
				System.out.println("Courses that need to be covered during week " + week + "\n");
				
				for(int i = 0; i < absenteeCoursesOfWeek.size(); i++) {
					if(i == 0) {
						System.out.println("Monday:");
					}
					if(i == 1) {
						System.out.println("Tuesday:");
					}
					if(i == 2) {
						System.out.println("Wednesday:");
					}
					if(i == 3) {
						System.out.println("Thursday:");
					}
					if(i == 4) {
						System.out.println("Friday:");
					}
					for(int j = 0; j < absenteeCoursesOfWeek.get(i).size(); j++) {
						System.out.println(absenteeCoursesOfWeek.get(i).get(j));
					}
				}
				
				System.out.println();
				ArrayList<Course> absenteeCourses = absenteeCoursesOfWeek.get(dayOfWeek);
				
				System.out.println();
				
				System.out.println("Courses that need to be covered during week " + week + " and day " + dayOfWeek + "\n");
	
				for(int j = 0; j < absenteeCourses.size(); j++) {
					System.out.println(absenteeCourses.get(j));
				}
				
				System.out.println();
				System.out.println("On Calls during Month = " + month + ", Week = " + week + ", Day = " + dayOfWeek);
				System.out.println("----------------------------------------------------------------------\n");		
				
				//Test OnCallProcessor
				OnCallProcessor ocp = new OnCallProcessor(teachers,absenteeCourses,max_on_calls,month,dayOfWeek);
				
				if (ocp.generateOnCallList()) {
					System.out.println(ocp);
				}
			}
			else {
				System.out.println("Schedule is NOT in correct format. Please check headers");			
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

import com.team6.*;
import java.util.ArrayList;

public class Application {
	public static void main(String args[]) {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			AbsencesProcessor ap = new AbsencesProcessor(1);
			//TallyProcessor tp = new TallyProcessor();
			
			if(sp.checkScheduleFormat()) {
				System.out.println("Schedule has correct headers");
			}
			else {
				System.out.println("Schedule is NOT in correct format. Please check headers");			
			}
			
			System.out.println();
			
			ArrayList<Teacher> teachers = sp.generateTeachers();
			//tp.calculateOnCallsTotal(teachers);
			for (int i = 0; i < teachers.size(); i++) {
				System.out.println(teachers.get(i));
			}
			
			ArrayList<ArrayList<Course>> courses = ap.findAbsences(teachers);
			
			
			System.out.println("Courses that need to be covered");
			for(int i = 0; i < courses.size(); i++) {
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
				for(int j = 0; j < courses.get(i).size(); j++) {
					System.out.println(courses.get(i).get(j).teacher + ": " + courses.get(i).get(j));
				}
			}
			
			ArrayList<SupplyTeacher> supplies = ap.generateSupplyList();
			System.out.println("Supplies this week: ");
			for(int i = 0; i < supplies.size(); i++) {
				System.out.println(supplies.get(i).getCode() + " : " + supplies.get(i).getName());
			}
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

import com.team6.*;
import java.util.ArrayList;

public class Application {
	public static void main(String args[]) {
		
		try {
			ScheduleProcessor sp = new ScheduleProcessor();

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

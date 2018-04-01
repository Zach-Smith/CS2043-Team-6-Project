import com.team6.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class Application {
	public static void main(String args[]) throws InvalidFormatException, IOException {
		
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
				
				System.out.println("----------------------------------------------------------------------\n");
				System.out.println("After reading weekly, monthly, and total on-call tallies for each teacher (from on-call tallies sheet):");
				
				//Test ReadOnCallTally
				ArrayList<ArrayList<String>> onCallTally = ReadOnCallTally.readOnCallTally(ReadOnCallTally.getSheetByMonth());
				teachers = ReadOnCallTally.updateTeachersFromOnCall(onCallTally, teachers);
				for (int i=0; i<=teachers.size()-1; i++)
					System.out.println(teachers.get(i).toString());
				
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
				
				//Test SupplyProcessor
				System.out.println("Courses Covered by Supply Teachers:");
				SupplyProcessor sup = new SupplyProcessor(week);
				ArrayList<SupplyTeacher> supplyTeachers = sup.generateSupplyList();
				ArrayList<OnCall> onCalls = sup.assignSupplyTeacher(teachers, supplyTeachers);
				for(int i = 0; i < onCalls.size(); i++) {
					System.out.println(onCalls.get(i));
					System.out.println();
				}	
			}
			else {
				System.out.println("Schedule is NOT in correct format. Please check headers");			
			}
			
			
			// Test WorkbookWriter		
			WorkbookWriter.writeAbsences("MC",1,2,"Period 1","./OnCall_Tallies_Example_edited.xlsx","updated-file.xlsx");
			
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
}

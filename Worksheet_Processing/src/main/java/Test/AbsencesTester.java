package Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import com.team6.AbsencesProcessor;
import com.team6.Course;
import com.team6.ScheduleProcessor;
import com.team6.SupplyProcessor;
import com.team6.SupplyTeacher;
import com.team6.Teacher;

public class AbsencesTester {

	@Test
	public void getUncoveredClasses() {
		ScheduleProcessor sp;
		try {
			sp = new ScheduleProcessor();
			ArrayList<Teacher> teachers = sp.generateTeachers();
			AbsencesProcessor ap = new AbsencesProcessor(1);
			ArrayList<ArrayList<Course>> absenteeCoursesOfWeek = ap.findAbsences(teachers);
			assertEquals(absenteeCoursesOfWeek.get(0).size(), 0);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void numOfSupplyTeachers() {
		try {
			SupplyProcessor supplyProcessor = new SupplyProcessor(1);
			ArrayList<SupplyTeacher> supplyTeachers = supplyProcessor.generateSupplyList();
			assertEquals(supplyTeachers.size(), 5);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void firstSupplyTeacher() {
		try {
			SupplyProcessor supplyProcessor = new SupplyProcessor(1);
			ArrayList<SupplyTeacher> supplyTeachers = supplyProcessor.generateSupplyList();
			assertEquals(supplyTeachers.get(0).getName(), "IDK");
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void firstUncoveredCourse() {
		ScheduleProcessor sp;
		try {
			sp = new ScheduleProcessor();
			ArrayList<Teacher> teachers = sp.generateTeachers();
			AbsencesProcessor ap = new AbsencesProcessor(1);
			ArrayList<ArrayList<Course>> absenteeCoursesOfWeek = ap.findAbsences(teachers);
			assertEquals(absenteeCoursesOfWeek.get(1).get(0).getCourseNumber(), "L");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

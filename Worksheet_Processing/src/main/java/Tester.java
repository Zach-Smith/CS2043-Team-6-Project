import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import com.team6.AbsencesProcessor;
import com.team6.Course;
import com.team6.ScheduleProcessor;
import com.team6.Teacher;

public class Tester {
	
	@Test
	public void numOfTeachers() {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			ArrayList<Teacher> teachers = sp.generateTeachers();
			assertEquals(teachers.size(), 8);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getFirstTeacher() {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			ArrayList<Teacher> teachers = sp.generateTeachers();
			assertEquals(teachers.get(0).getInitials(), "RA");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void getUncoveredClasses() {
		ScheduleProcessor sp;
		try {
			sp = new ScheduleProcessor();
			ArrayList<Teacher> teachers = sp.generateTeachers();
			AbsencesProcessor ap = new AbsencesProcessor(1);
			ArrayList<ArrayList<Course>> absenteeCoursesOfWeek = ap.findAbsences(teachers);
			assertEquals(absenteeCoursesOfWeek.get(0).size(), 2);
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

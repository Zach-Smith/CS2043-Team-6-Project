package Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import com.team6.ScheduleProcessor;
import com.team6.ScheduleReader;
import com.team6.Teacher;

public class ScheduleTester {

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
	public void getLastTeacher() {
		try {
			ScheduleProcessor sp = new ScheduleProcessor();
			ArrayList<Teacher> teachers = sp.generateTeachers();
			assertEquals(teachers.get(teachers.size()-1).getInitials(), "MC");
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test() throws IOException, InvalidFormatException {
		//try {
			ScheduleReader sr = new ScheduleReader();
			boolean checkSchedule = sr.checkScheduleFormat();
		//}
		//catch(Exception e) {}
		
		assertTrue(checkSchedule);
	}

}

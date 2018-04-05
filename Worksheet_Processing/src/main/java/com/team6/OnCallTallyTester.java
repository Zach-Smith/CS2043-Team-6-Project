package com.team6;

import java.io.*;
import static org.junit.Assert.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

public class OnCallTallyTester {
	
	
	
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

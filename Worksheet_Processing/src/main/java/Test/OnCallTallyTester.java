package Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;

import com.team6.ReadOnCallTally;


public class OnCallTallyTester {
	
	@Test
	public void firstTeacherInTally() {
		try {
			ArrayList<ArrayList<String>> onCallTally = ReadOnCallTally.readOnCallTally(0);
			assertEquals(onCallTally.get(7).get(1), "RA");
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void lastMonthInTally() {
		try {
			ArrayList<ArrayList<String>> onCallTally = ReadOnCallTally.readOnCallTally(4);
			assertEquals(onCallTally.get(2).get(2), "January");
			
		} catch (InvalidFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

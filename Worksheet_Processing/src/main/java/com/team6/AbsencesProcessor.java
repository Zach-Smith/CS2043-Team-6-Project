package com.team6;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.Iterator;


public class AbsencesProcessor {
	 public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx"; 	
	 private Workbook workbook;
	 private Sheet sheet;
	 
	 public AbsencesProcessor(int week) throws IOException,InvalidFormatException {
		  workbook = WorkbookFactory.create(new File(XLSX_FILE_PATH));
		  sheet = workbook.getSheetAt(week);  
	  }
	 
	 public ArrayList<ArrayList<String>> readAbsences() throws IOException,InvalidFormatException{
		 
		 DataFormatter formatter = new DataFormatter();
		 
		 ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		 int index = 0;
		 arr.add(new ArrayList<String>());
		 
		 Iterator<Row> rowIterator = sheet.rowIterator();
		 while (rowIterator.hasNext()) {
			 Row row = rowIterator.next();
			 
			 Iterator<Cell> cellIterator = row.cellIterator();

			 while (cellIterator.hasNext()) {
				 Cell cell = cellIterator.next();
				 String cellValue = formatter.formatCellValue(cell);
				 if(cellValue.equals("")) {
					 arr.get(index).add("a");
	                	}else {
	                		arr.get(index).add(cellValue);
	                	}
			 }
			 arr.add(new ArrayList<String>());
			 index++;
		 }
		 return arr;
	 }
	 	
	 //dayOfWeek ranges from 0 (Monday) to 4 (Friday)
	 public ArrayList<Course> findAbsencesForOneDay(ArrayList<Teacher> teachers, int dayOfWeek) throws InvalidFormatException, IOException{
			 ArrayList<ArrayList<String>> arr = this.readAbsences();
			 ArrayList<Course> courses = new ArrayList<Course>();
			  
			 for(int i = 3; !arr.get(i).get(0).equals("a"); i++) {
				 String initials = arr.get(i).get(0);
				 int index;
				 for(index = 0;index < teachers.size(); index++) {
					 if(initials.equals(teachers.get(index).initials))
						 break;
				  }	
				
				 int scheduleIndex = 0;	
				 for(int j = 2 + dayOfWeek * 5; j < 2 + dayOfWeek *5 + 5 ; j++) {
					 if(arr.get(i).get(j).equals("a")) {
						 courses.add(teachers.get(index).schedule.get(scheduleIndex));
					 }	
					 scheduleIndex++;
					 
				 }
			 }
			 return courses;
	 }
	
}



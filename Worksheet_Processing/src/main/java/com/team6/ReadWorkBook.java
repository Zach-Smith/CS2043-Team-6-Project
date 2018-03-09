package com.team6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;

public class ReadWorkBook{
	
	  public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx";
	  public static final String XLSX_FILE_PATH2 = "./OnCall_Tallies_Example_edited.xlsx";
	  
	  public static ArrayList<Teacher> teachers = new ArrayList<Teacher>();
	

	  public static ArrayList<ArrayList<String>> readSchedule() throws IOException,InvalidFormatException{
		  Workbook w = WorkbookFactory.create(new File(XLSX_FILE_PATH));
		  
		  Sheet sheet = w.getSheetAt(0);
		  		  		  		  
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
	                arr.get(index).add(cellValue);
	            }
	            arr.add(new ArrayList<String>());
	            index++;
	        }
	        
	        return arr;
	  }
	  
	  public static ArrayList<ArrayList<String>> readAbsences(int week) throws IOException,InvalidFormatException{
		  Workbook w = WorkbookFactory.create(new File(XLSX_FILE_PATH));
		  
		  Sheet sheet = w.getSheetAt(week);
		  		  		  		  
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
	                arr.get(index).add(cellValue);
	            }
	            arr.add(new ArrayList<String>());
	            index++;
	        }
	        return arr;
	  }
	  
	  public static ArrayList<ArrayList<String>> readOnCallTally() throws IOException,InvalidFormatException{
		  Workbook w = WorkbookFactory.create(new File(XLSX_FILE_PATH2));
		  
		  Sheet sheet = w.getSheetAt(0);
		  		  		  		  
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
	                arr.get(index).add(cellValue);
	            }
	            arr.add(new ArrayList<String>());
	            index++;
	        }
	        return arr;
	  }
	  public static ArrayList<Teacher> generateTeachersFromOnCall(ArrayList<ArrayList<String>> onCall){
		  //Row 8 is where teachers start to be listed. Initials are in column 2
		  for (int i=8; i<=onCall.get(1).size()-1; i++)
			  	  //If the teacher doesn't already exist in the array, add them
				 if (!checkForDuplicate(onCall.get(1).get(i)))
					  teachers.add(new Teacher(onCall.get(1).get(i)));
					  
		  return teachers;
		  
		  
	  }
	  
	  private static boolean checkForDuplicate(String initials) {
		  for (int i=0; i<=teachers.size()-1; i++)
			  if (initials.equals(teachers.get(i).getInitials()))
				  return false;
		  return true;
	  }
	  
}
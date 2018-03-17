package com.team6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;

public class ReadWorkBook{
	
	  
	
	  
	  public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx";
	  public static final String XLSX_FILE_PATH2 = "./OnCall_Tallies_Example_edited.xlsx";
	
	  
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
	                if(cellValue.equals(""))
	                	arr.get(index).add("a");
	                else 
	                	arr.get(index).add(cellValue);
	            }
	            arr.add(new ArrayList<String>());
	            index++;
	        }
	        return arr;
	  }
	  
	  public static ArrayList<Teacher> generateTeachersFromOnCall(ArrayList<ArrayList<String>> onCall){
		  ArrayList<Teacher> teachers = new ArrayList<Teacher>();
		  //Row 8 is where teachers start to be listed. Initials are in column 2
		  Teacher temp = new Teacher("");
		  String initials;
		  //for (int i=7; i<=onCall.get(1).size()-1; i++) {    <-- This doesn't work for some reason
		  for (int i=7; i<=100; i++) {
			  initials = onCall.get(i).get(1);
			  
			  //Removes unnecessary spaces from some initals
			  initials = initials.trim();
			  System.out.println(onCall.get(i).get(1));
			  //Teacher initials can be between lengths 2 and 4
			  if(initials.length()>=2 && initials.length()<=4) {
				temp = new Teacher(initials);
				temp.setTotalOnCalls(calculateOnCallsTotal(onCall.get(i)));
				temp.setOnCallsWeek(calculateOnCallsWeek(i, onCall));
			  	
				if (!checkForDuplicate(temp.getInitials(), teachers))
					  teachers.add(temp);
			  }	
		  }
		  return teachers;
		  
		  
	  }
	  
	  
	  private static boolean checkForDuplicate(String initials, ArrayList<Teacher> teachers) {
		  initials = initials.trim();
		  for (int i=0; i<=teachers.size()-1; i++) {
			  if (initials.equals(teachers.get(i).getInitials())) {
				  System.out.println("duplicate");
				  return true;
			  }
		  }
		  return false;
	  }
	  
	  private static int calculateOnCallsTotal(ArrayList<String> row) {
		  int count=0;
		  for(int i=1; i<=row.size()-2; i++)
			  if(row.get(i).equals("1"))
				  count++;
		  return count;
		  
	  }
	  
	  private static int calculateOnCallsWeek(int row, ArrayList<ArrayList<String>> tallies) {
		  System.out.println(tallies.size());
		  System.out.println(tallies.get(13).size());
		  for(int i=0; i<=tallies.get(13).size()-1; i++)
			  System.out.println(tallies.get(i));
		  
		  int count=0;
		  int column=0;

		  Calendar now = Calendar.getInstance();
		  int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);
		  //I set dayOfMonth=8 for testing
		  dayOfMonth = 8;
		  String day = String.valueOf(dayOfMonth);
		  
		  //Finds the column that the day of the month is contained in
		  for(int i=0; i<=tallies.get(5).size()-1; i++) {
			  //Row five contains day of month
			  if(tallies.get(5).get(i).equals(day)) {
				  column = i;
				  break;
			  }
		  }
		  
		  //If the day of the month wasn't found, that means the system was run on a weekend, or the days
		  //of the month weren't enetered in the spreadsheet properly
		  if(column==0)
			  return 0;
		  
		  //Row five contains day of week
		  //Basically, we want to iterate 5 days ahead from Monday to count the weekly Oncall tally
		  if(tallies.get(4).get(column).equals("M")) {
			  for(int i=column; i<=column+5; i++) {
				  //Case where week carries over to next month (will work on this later)
				  //if(tallies.get(4).get(i).equals(""))
				  System.out.println(tallies.get(row).get(i)); 
				  if(tallies.get(row).get(i).equals("1"))
					  count++;
			  }
			  return count;  
		  }
		  
		  //The situation where the day of the month doesn't land on a Monday
		  else {
			  //We first must iterate back to the first Monday we find
			  System.out.println(column);
			  for(int i=column; i>=column-5; i--)
				  if(tallies.get(4).get(i).equals("M")) {
					  column=i;
					  break;
				  }
	
			  for(int i=column; i<=column+5; i++)
				  //System.out.println(tallies.get(row).get(i));
				  if(tallies.get(row).get(i).equals("1"))
					  count++;
			  return count;
		  }
		  
	  }
	  
	  
}
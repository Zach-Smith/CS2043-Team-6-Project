package com.team6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;
import java.text.*;


public class ReadOnCallTally {
	
	  public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx";
	  public static final String XLSX_FILE_PATH2 = "./OnCall_Tallies_Example_edited.xlsx";
	  public static final int DAY_OF_MONTH_ROW = 5;
	  public static final int DAY_OF_WEEK_ROW = 4;
	  public static final int MONTH_WORD_ROWCOLUMN = 2;
	
	  
	  public static ArrayList<ArrayList<String>> readOnCallTally(int workSheet) throws IOException,InvalidFormatException{
		  Workbook w = WorkbookFactory.create(new File(XLSX_FILE_PATH2));

		  //Implement a getMonth() method so that it knows what sheet to read
		  Sheet sheet = w.getSheetAt(workSheet);
		  		  		  		  
		  DataFormatter formatter = new DataFormatter();
		   
		  ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		  int index = 0;
		  arr.add(new ArrayList<String>());
		  
		  Iterator<Row> rowIterator = sheet.rowIterator();
	      while (rowIterator.hasNext()) {
	            Row row = rowIterator.next();

	            for (short i=0; i<=row.getLastCellNum()-1; i++) {

	                Cell cell = row.getCell(i, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
	                if (cell == null)
	                	arr.get(index).add("a");
	                else {
	                	String cellValue = formatter.formatCellValue(cell);
	                	arr.get(index).add(cellValue);
	                }
	                
	            }
	            arr.add(new ArrayList<String>());
	            index++;
	        }
	        return arr;
	  }
	  
	  public static ArrayList<Teacher> updateTeachersFromOnCall(ArrayList<ArrayList<String>> onCall, ArrayList<Teacher> teachers){
		 
		  String initials;
		  
		  for (int i=7; i<=onCall.size()-2; i++) {
			  initials = onCall.get(i).get(1);
			  initials = initials.trim();
			  
			  int teacher = findTeacher(teachers, initials);
			  if (teacher >= 0) {
					int currentWeekly = teachers.get(teacher).getOnCallsWeek();
					int currentMonthly = teachers.get(teacher).getOnCallsMonth();
					int currentTotal = teachers.get(teacher).getOnCallsTotal();
				    teachers.get(teacher).setOnCallsMonth(currentMonthly + calculateOnCallsMonth(onCall.get(i)));
					teachers.get(teacher).setOnCallsWeek(currentWeekly + calculateOnCallsWeek(i, onCall));
					teachers.get(teacher).setOnCallsTotal(currentTotal + calculateOnCallsTotal(i));
				}
		  }
		  return teachers;  
	  }
	  
	  
	  private static int findTeacher(ArrayList<Teacher> teachers, String initials) {
		  for(int i=0; i<=teachers.size()-1; i++)
			  if(teachers.get(i).getInitials().equals(initials))
				  return i;
		  return -1;
	  }
	  
	  
	  private static int calculateOnCallsMonth(ArrayList<String> row) {
		  int count=0;
		  for(int i=1; i<=row.size()-2; i++)
			  if(row.get(i).equals("1"))
				  count++;
		  return count;
	  }
	  
	  private static int calculateOnCallsTotal(int row){
		  int count=0;
		  ArrayList<ArrayList<String>> sheet = new ArrayList<ArrayList<String>>();
		  for(int i=0; i<= 6; i++) {
			  try {
				  sheet = readOnCallTally(i);
			  }
			  catch(IOException e){
	  			  //e.printStackTrace();
	  		  }
	  		  catch(InvalidFormatException e){
	  			  //e.printStackTrace();
	  		  }
			  catch(IllegalArgumentException e) {
				  break;
			  }
			  count += calculateOnCallsMonth(sheet.get(row));
			  
		  }
		  return count;
	  }
	  
	  private static int calculateOnCallsWeek(int row, ArrayList<ArrayList<String>> tallies){
		  int count=0;
		  int column=0;
		  int potentialPreviousMonth=0;

		  Calendar now = Calendar.getInstance();
		  int dayOfMonth = now.get(Calendar.DAY_OF_MONTH);

		  String day = String.valueOf(dayOfMonth);
		  
		  //Finds the column that the day of the month is contained in
		  for(int i=0; i<=tallies.get(DAY_OF_MONTH_ROW).size()-1; i++) {
			  if(tallies.get(DAY_OF_MONTH_ROW).get(i).equals(day)) {
				  column = i;
				  break;
			  }
		  }
		  
		  //If the day of the month wasn't found, that means the system was run on a weekend, or the days
		  //of the month weren't enetered in the spreadsheet properly
		  if (column != 0) {
			  boolean foundMonday = false;
			  
			  if(tallies.get(DAY_OF_WEEK_ROW).get(column).equals("M")) {
				  count = getWeekOnCall(tallies.get(row), column, column+4);
			  }
			  
			  //The situation where the current day of the month doesn't land on a Monday
			  else {

				  //We first must iterate back to the first Monday we find
				  for(int i=column; i>=0; i--) {
					  if(tallies.get(DAY_OF_WEEK_ROW).get(i).equals("M")) {
						  column = i;
						  count = getWeekOnCall(tallies.get(row), column, column+4);
						  foundMonday = true;
						  break;
					  }
				  }
				  if(!foundMonday) {
				  		  column = 2;
				  		  //This loop calculates the num of onCalls for the partial week
				  		  for(int j=2; j<=6; j++) {
				  			  if(tallies.get(row).get(j).equals("1"))
				  				  count++;
				  		  }

				  		  try {
				  			  int lastMonthSheet = getSheetByMonth()-1;
				  			  ArrayList<ArrayList<String>> lastMonth = readOnCallTally(lastMonthSheet);
					  		  potentialPreviousMonth = getPreviousMonthWeekTally(lastMonth, row);
				  		  }
				  		  catch(IOException e){
				  			  e.printStackTrace();
				  		  }
				  		  catch(InvalidFormatException e){
				  			  e.printStackTrace();
				  		  }
				  		  
				  	  }
				  
			  }
		  }
		  return count + potentialPreviousMonth;
	  }
	  

	  public static int getSheetByMonth() throws IOException, InvalidFormatException {
		  int currentMonth=0;
		  Calendar cal = Calendar.getInstance();
		  String month = new SimpleDateFormat("MMMM").format(cal.getTime());
		  ArrayList<ArrayList<String>> temp;
		  for(int i=0; i<=6; i++) {
			  temp = readOnCallTally(i);
			  if (temp.get(MONTH_WORD_ROWCOLUMN).get(MONTH_WORD_ROWCOLUMN).equals(month)) {
		  		currentMonth = i;
		  		break;
			  }
		  }
		  return currentMonth;
	  }
	  
	  
	  public static int getWeekOnCall(ArrayList<String> row, int start, int end) {
		  int count=0;
		  for(int i=start; i<=end; i++)
			  if (row.get(i).equals("1"))
				  count++;
		  return count;
	  }
	  
	  
	  public static int getPreviousMonthWeekTally(ArrayList<ArrayList<String>> tallies, int row) {
		  int monday = findLastMonday(tallies.get(DAY_OF_WEEK_ROW));
		  int count=0;
		  for(int i=monday; i<=monday+4; i++)
			  if(tallies.get(row).get(i).equals("1"))
				  count++;
		  return count;
	  }
	  
	  public static int findLastMonday(ArrayList<String> row) {
		  int rowOfMonday=0;
		  for (int i=row.size()-1; i>=1; i--)
			  if(row.get(i).equals("M")) {
				  rowOfMonday=i;
				  break;
			  }
		  return rowOfMonday;
	  }
}

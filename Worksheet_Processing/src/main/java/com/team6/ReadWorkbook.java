package com.team6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;

public class ReadWorkbook{
	
	  public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx";
	  public static final String XLSX_FILE_PATH2 = "./OnCall_Tallies_Example_edited.xlsx";
	
	  public static void main(String[] args) throws IOException,InvalidFormatException {
		  //ArrayList<ArrayList<String>> schedule = readSchedule();
		  ArrayList<ArrayList<String>> absenteeList = readAbsences(1);
		  //ArrayList<ArrayList<String>> onCallTally = readOnCallTally();
		  
		 
//		  System.out.println("Schedule:"); 
//		  for(int i = 0; i < schedule.size(); i++) {
//			  for(int j = 0; j < schedule.get(i).size(); j++) {
//				  System.out.print(schedule.get(i).get(j)+ '\t');
//			  }
//			  System.out.println();
//		  }
//		  
//		  System.out.println("Absences:"); 
//		  for(int i = 0; i < absenteeList.size(); i++) {
//			  for(int j = 0; j < absenteeList.get(i).size(); j++) {
//				  System.out.print(absenteeList.get(i).get(j)+ '\t');
//			  }
//			  System.out.println();
//		  }
//		  
//		  System.out.println("On-call Tally:"); 
//		  for(int i = 0; i < onCallTally.size(); i++) {
//			  for(int j = 0; j < onCallTally.get(i).size(); j++) {
//				  System.out.print(onCallTally.get(i).get(j)+ '\t');
//			  }
//			  System.out.println();
//		  }
		  System.out.println(absenteeList.get(1).get(1));
		  System.out.println(absenteeList.get(1).get(6));
	  }
	  
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
}

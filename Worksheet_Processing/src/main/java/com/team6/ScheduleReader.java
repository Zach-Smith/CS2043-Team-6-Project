package com.team6;

import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.Iterator;

public class ScheduleReader {
	
	  public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx"; 	
	  private Workbook workbook;
	  private Sheet scheduleSheet;
	  
	  public ScheduleReader() throws IOException,InvalidFormatException {
		  workbook = WorkbookFactory.create(new File(XLSX_FILE_PATH));
		  scheduleSheet = workbook.getSheetAt(0); //Corresponds to the regular teacher schedule
		  
	  }
	  
	  //Checks if headers are correct
	  public boolean checkScheduleFormat(){
		  
		  Iterator<Row> rowIt = scheduleSheet.rowIterator();
		  DataFormatter formatter = new DataFormatter();
		  
		  if(!rowIt.hasNext()) {
			  return false;
		  }
		  else {
			
			  Row row = rowIt.next();
		
			  Iterator<Cell> cellIt = row.cellIterator();
			  
			  int headerCount = 0;
			  
			  while (cellIt.hasNext()) {
	              Cell c = cellIt.next();
	              String columnHeader = formatter.formatCellValue(c);
	              
	              if (headerCount == 0 && columnHeader.equals("Name")) {
	            	  headerCount++;
	              }
	              else if (headerCount == 1 && columnHeader.equals("Period 1")) {
	            	  headerCount++;
	          	  }
	              else if (headerCount == 2 && columnHeader.equals("Period 2")) {
	            	  headerCount++;
	          	  }
	              else if (headerCount == 3 && columnHeader.equals("Period 3A")) {
	            	  headerCount++;
	          	  }
	              else if (headerCount == 4 && columnHeader.equals("Period 3B")) {
	            	  headerCount++;
	          	  }
	              else if (headerCount == 5 && columnHeader.equals("Period 4")) {
	            	  headerCount++;
	          	  }
	          }
		
			  return headerCount == 6;
		  }
	  }
	  
	  public ArrayList<ArrayList<String>> convertSpreadsheet(){
		  
		  ArrayList<ArrayList<String>> arr = new ArrayList<ArrayList<String>>();
		  
		  Iterator<Row> rowIt = scheduleSheet.rowIterator();
		  DataFormatter formatter = new DataFormatter();
		  
		  		  
		  while(rowIt.hasNext()) {
			  ArrayList<String> a = new ArrayList<String>();
			  Row row = rowIt.next();
			  	
			  Iterator<Cell> cellIt = row.cellIterator();
			  
			  for (int i = 0; i < 6; i++) {
				  
				  Cell c = cellIt.next();
				  String val = formatter.formatCellValue(c);
				  
				  a.add(val);
				  
			  }
			  if(a.get(0).equals("")) {
				  break;
			  }
			  arr.add(a);
					  
		  }
		  
		  return arr;
		  
	  }
}

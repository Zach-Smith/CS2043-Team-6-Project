package com.team6;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.*;

public class ReadWorkbook{
	
	  public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx";
	  public static final String XLSX_FILE_PATH2 = "./OnCall_Tallies_Example_edited.xlsx";
	  
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
}

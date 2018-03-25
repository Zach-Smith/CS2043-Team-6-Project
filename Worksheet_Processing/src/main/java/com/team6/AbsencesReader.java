package com.team6;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.Iterator;


public class AbsencesReader {
	 public static final String XLSX_FILE_PATH = "./Workbook-Term2017-2018W.xlsx"; 	
	 private Workbook workbook;
	 private Sheet sheet;
	 
	 public AbsencesReader(int week) throws IOException,InvalidFormatException {
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
}
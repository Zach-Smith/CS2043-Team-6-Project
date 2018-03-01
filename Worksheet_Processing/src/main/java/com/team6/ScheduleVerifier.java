import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import java.io.*;
import java.util.Iterator;

public class ScheduleVerifier {
	
	  public static final String XLSX_FILE_PATH = "./Teacher_Schedule_Example.xlsx"; 	
	
	  public static void main(String[] args) throws IOException,InvalidFormatException {
		  Workbook w = WorkbookFactory.create(new File(XLSX_FILE_PATH));
		  
		  System.out.println("Workbook file: " + XLSX_FILE_PATH);
		  System.out.println("Sheet name:  " + w.getSheetName(0));
		  
		  Sheet s = w.getSheetAt(0);
		  		  		  		  
		  DataFormatter formatter = new DataFormatter();
		  
		  System.out.println("\n\nChecking Column headers...");
		  
		  Iterator<Row> rowIt = s.rowIterator();
		  
		  Row row = rowIt.next();
	
		  Iterator<Cell> cellIt = row.cellIterator();
		  
		  int headerCount = 0;
		  int teacherNum = 0;
		  
		  while (cellIt.hasNext()) {
              Cell c = cellIt.next();
              String columnHeader = formatter.formatCellValue(c);
              System.out.print(columnHeader + "\t");
              
              if (headerCount == 0 && columnHeader.equals("Name ")) {
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
		  
		  System.out.println();
		  
		  
		  if (headerCount == 6) {
			 System.out.println("All 6 column headers are there.");			 
		  }
		  
		  		  
		  while(rowIt.hasNext()) {
			  teacherNum++;
			  rowIt.next();
		  }
		  
		  System.out.println("There are " + teacherNum + " teachers in the schedule.");

		  if (headerCount == 6 && teacherNum > 0) {
			  System.out.println("Schedule is correctly formatted.");
		  }
		  else {
			  System.out.println("Schedule is not correctly formatted");
		  }	  
		  
	  }

}

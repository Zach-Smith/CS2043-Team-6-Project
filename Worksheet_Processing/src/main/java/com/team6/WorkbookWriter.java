package com.team6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;

public class WorkbookWriter {

	public static final int STARTING_ROW = 6;
	public static final int STARTING_CELL = 1;

	public static void writeAbsences(String teacherName, int month, int termStartMonth,  int day, String period, String inputFile)
			throws InvalidFormatException, IOException {
		Workbook workbook = readOnCallTally(inputFile, teacherName, period, day, month, termStartMonth);
		String outputFile = inputFile;
		write(workbook, outputFile);
	}

	private static void write(Workbook workbook, String outputFile) throws IOException {

		FileOutputStream fileOut = new FileOutputStream(outputFile);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
	}

	private static Workbook readOnCallTally(String xlsFilePath,String teacherName, String period,
			int day, int month, int termStartMonth) throws IOException, InvalidFormatException {

		DataFormatter formatter = new DataFormatter();
		String currentPeriod = "";
		
		int worksheet = termStartMonth - month;
		
		FileInputStream file = new FileInputStream(xlsFilePath);
		 XSSFWorkbook workbook = new XSSFWorkbook(file);
		 XSSFSheet sheet = workbook.getSheetAt(worksheet);
		 

		for (short currentRow = STARTING_ROW; currentRow < sheet.getLastRowNum(); currentRow++) {
			Row row = sheet.getRow(currentRow);

			String cellValue = formatter.formatCellValue(row.getCell(STARTING_CELL));
			if (cellValue.equals("Period 1")) {
				currentPeriod = "Period 1";
			} else if (cellValue.equals("Period 2")) {
				currentPeriod = "Period 2";
			} else if (cellValue.equals("Period 3")) {
				currentPeriod = "Period 3";
			} else if (cellValue.equals("Period 4")) {
				currentPeriod = "Period 4";
			}

			if (cellValue.equals(teacherName) && currentPeriod.equals(period)) {
				for (short currentCell = STARTING_CELL; currentCell < row.getLastCellNum(); currentCell++) {

					Cell cell = row.getCell(currentCell,
							org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					int weekendDays = (day / 5) *2; 
					if (currentCell - 1 == day + weekendDays) {
						cell.setCellValue(1);
					}
				}

			}
		}
		return workbook;
	}
	
private static int getWorksheetNumber (int month) {
		int worksheet = 0;
		
		if (month == 1 || month == 6) // month my change depending on when the actual semester starts
			worksheet = 0;
		else if (month == 2 || month == 7) 
			worksheet = 1;
		else if (month == 3 || month == 8) 
			worksheet = 2;
		else if (month == 4 || month == 9) 
			worksheet = 3;
		return worksheet;
	}
}



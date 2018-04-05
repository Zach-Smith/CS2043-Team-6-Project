package com.team6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.*;

public class WorkbookWriter {

	public static final int STARTING_ROW = 6;
	public static final int STARTING_CELL = 1;

	public static void writeTally(String teacherName, int year, int month, int termStartMonth,  int day, String period, String inputFile)
			throws InvalidFormatException, IOException {
		Workbook workbook = readOnCallTally(inputFile, teacherName, period, day, month, year,termStartMonth);
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
			int day, int month, int year, int termStartMonth) throws IOException, InvalidFormatException {

		DataFormatter formatter = new DataFormatter();
		String currentPeriod = "";
		boolean teacherFound = false;
		int[] startCollumsPeriodes = new int[5];
		int templateRowIndex = 0;
		
		int worksheet;
		
		if (month >= termStartMonth) {
			worksheet =  month - termStartMonth;
		}
		else {
			worksheet = month - termStartMonth + 12;
		}
		
		FileInputStream file = new FileInputStream(xlsFilePath);
		XSSFWorkbook workbook = new XSSFWorkbook(file);
		XSSFSheet sheet = workbook.getSheetAt(worksheet);
		 
while(teacherFound == false) {
		
		for (short currentRow = STARTING_ROW; currentRow < sheet.getLastRowNum(); currentRow++) {
			
			Row row = sheet.getRow(currentRow);

			String cellValue = formatter.formatCellValue(row.getCell(STARTING_CELL));
			if (cellValue.equals("Period 1")) {
				currentPeriod = "Period 1";
				startCollumsPeriodes[0] = currentRow;
			} else if (cellValue.equals("Period 2")) {
				currentPeriod = "Period 2";
				startCollumsPeriodes[1] = currentRow;
			} else if (cellValue.equals("Period 3a")) {
				currentPeriod = "Period 3a";
				startCollumsPeriodes[2] = currentRow;
			} else if (cellValue.equals("Period 3b")) {
				currentPeriod = "Period 3b";
				startCollumsPeriodes[3] = currentRow;
			} else if (cellValue.equals("Period 4")) {
				currentPeriod = "Period 4";
				startCollumsPeriodes[4] = currentRow;
			}else if (cellValue.equals("don't write in this row")) {
				templateRowIndex = currentRow;
			}
				

			if (cellValue.equals(teacherName) && currentPeriod.equals(period)) {
				for (short currentCell = STARTING_CELL + 1; currentCell < row.getLastCellNum(); currentCell++) {

					Cell cell = row.getCell(currentCell,
							org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					//int weekendDays = (day / 5) *2; 
					if (currentCell == getCellValue(year,month,day)) {
						
						cell.setCellValue(1);
						teacherFound = true;
					}
				}

			}
		}
		if (teacherFound == false) {
			createNewRow(workbook, sheet, startCollumsPeriodes,teacherName,period,day,templateRowIndex);
			System.out.println("A new row for the teacher has been created.");
		}
}
		
		return workbook;
	}

public static int getCellValue(int year,int month,int day) {
	int count = 0;
	Calendar cal = Calendar.getInstance();
	
	
	for (int i = 0; i < day; i++) {
		cal.set(year,month - 1,1 + i);
		
		if (cal.get(Calendar.DAY_OF_WEEK) == 1 || cal.get(Calendar.DAY_OF_WEEK) == 7) {
		}
		else {
			count++;
		}
	}
	
	count++;
	
	return count;
	
	
}
	
private static void createNewRow(Workbook workbook, XSSFSheet sheet, int[] startCollumsPeriodes,String teacherName, String period,
		int day, int templateRowIndex) {
	int indexForNewRow = 0;
	if (period.equals("Period 1")) {
		indexForNewRow = startCollumsPeriodes[1] -2;
	} else if (period.equals("Period 2")) {
		indexForNewRow = startCollumsPeriodes[2] -2;
	} else if (period.equals("Period 3a")) {
		indexForNewRow = startCollumsPeriodes[3] -2;
	} else if (period.equals("Period 3b")) {
		indexForNewRow = startCollumsPeriodes[4] -2;
	} else if (period.equals("Period 4")) {
		indexForNewRow = templateRowIndex -2;
	}
	//Row templateRow = sheet.getRow(templateRowIndex);
	sheet.shiftRows(indexForNewRow,sheet.getLastRowNum(),1);   
	//Row newRow = sheet.createRow(indexForNewRow);
	
	copyRow(workbook,  sheet, templateRowIndex, indexForNewRow);
	
	sheet.getRow(indexForNewRow).getCell(STARTING_CELL).setCellValue(teacherName);
	double newCellNumber =sheet.getRow(indexForNewRow-1).getCell(STARTING_CELL-1).getNumericCellValue()+1; 
	sheet.getRow(indexForNewRow).getCell(STARTING_CELL-1).setCellValue(newCellNumber);
	
	//for (short currentCell = 0; currentCell < templateRow.getLastCellNum(); currentCell++) 
	
}

private static void copyRow(Workbook workbook, Sheet worksheet, int templateRowIndex, int indexForNewRow) {
        Row newRow = worksheet.getRow(indexForNewRow);
        Row sourceRow = worksheet.getRow(templateRowIndex);

       /* if (newRow != null) {
            worksheet.shiftRows(indexForNewRow, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(indexForNewRow);
        }*/

        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell TemplateCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            if (TemplateCell == null) {
                newCell = null;
                continue;
            }

            CellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(TemplateCell.getCellStyle());
            newCell.setCellStyle(newCellStyle);
            
            switch (TemplateCell.getCellTypeEnum()) {
                case BLANK:
                    newCell.setCellValue(TemplateCell.getStringCellValue());
                    break;
                case BOOLEAN:
                    newCell.setCellValue(TemplateCell.getBooleanCellValue());
                    break;
                case ERROR:
                    newCell.setCellErrorValue(TemplateCell.getErrorCellValue());
                    break;
                case FORMULA:
                    newCell.setCellFormula(TemplateCell.getCellFormula());
                    break;
                case NUMERIC:
                    newCell.setCellValue(TemplateCell.getNumericCellValue());
                    break;
                case STRING:
                    newCell.setCellValue(TemplateCell.getRichStringCellValue());
                    break;
            }
        }
    }
	
private static int getWorksheetNumber (int month) { // is at the moment not in use, since we don't know how long semesters are.
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

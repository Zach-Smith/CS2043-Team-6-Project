package com.team6;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
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
		boolean teacherFound = false;
		int[] startCollumsPeriodes = new int[5];
		int templateRowIndex = 0;
		
		int worksheet = termStartMonth - month;
	
		
		FileInputStream file = new FileInputStream(xlsFilePath);
		 XSSFWorkbook workbook = new XSSFWorkbook(file);
		 XSSFSheet sheet = workbook.getSheetAt(worksheet);
		 

		for (short currentRow = STARTING_ROW; currentRow < sheet.getLastRowNum(); currentRow++) {
			Row row = sheet.getRow(currentRow);

			String cellValue = formatter.formatCellValue(row.getCell(STARTING_CELL));
			if (cellValue.equals("Period 1")) {
				currentPeriod = "Period 1";
				startCollumsPeriodes[0] = currentRow;
			} else if (cellValue.equals("Period 2")) {
				currentPeriod = "Period 2";
				startCollumsPeriodes[1] = currentRow;
			} else if (cellValue.equals("Period 3")) {
				currentPeriod = "Period 3";
				startCollumsPeriodes[2] = currentRow;
			} else if (cellValue.equals("Period 4")) {
				currentPeriod = "Period 4";
				startCollumsPeriodes[3] = currentRow;
			} else if (cellValue.equals("Period 5")) {
				currentPeriod = "Period 5";
				startCollumsPeriodes[4] = currentRow;
			}else if (cellValue.equals("down't write in this row")) {
				templateRowIndex = currentRow;
			}
	

			if (cellValue.equals(teacherName) && currentPeriod.equals(period)) {
				for (short currentCell = STARTING_CELL; currentCell < row.getLastCellNum(); currentCell++) {

					Cell cell = row.getCell(currentCell,
							org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					int weekendDays = (day / 5) *2; 
					if (currentCell - 1 == day + weekendDays) {
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
		return workbook;
	}
	
private static void createNewRow(Workbook workbook, XSSFSheet sheet, int[] startCollumsPeriodes,String teacherName, String period,
		int day, int templateRowIndex) {
	int indexForNewRow = 0;
	if (period.equals("Period 1")) {
		indexForNewRow = startCollumsPeriodes[0] -2;
	} else if (period.equals("Period 2")) {
		indexForNewRow = startCollumsPeriodes[0] -2;
	} else if (period.equals("Period 3")) {
		indexForNewRow = startCollumsPeriodes[0] -2;
	} else if (period.equals("Period 4")) {
		indexForNewRow = startCollumsPeriodes[0] -2;
	} else if (period.equals("Period 5")) {
		indexForNewRow = startCollumsPeriodes[0] -2;
	}
	//Row templateRow = sheet.getRow(templateRowIndex);
	sheet.shiftRows(indexForNewRow,sheet.getLastRowNum(),1);   
	//Row newRow = sheet.createRow(indexForNewRow);
	
	copyRow(workbook,  sheet, templateRowIndex, indexForNewRow);
	
	//for (short currentCell = 0; currentCell < templateRow.getLastCellNum(); currentCell++) 
	
}

	private static void copyRow(Workbook workbook, Sheet worksheet, int sourceRowNum, int destinationRowNum) {
        // Get the source / new row
        Row newRow = worksheet.getRow(destinationRowNum);
        Row sourceRow = worksheet.getRow(sourceRowNum);

        // If the row exist in destination, push down all rows by 1 else create a new row
        if (newRow != null) {
            worksheet.shiftRows(destinationRowNum, worksheet.getLastRowNum(), 1);
        } else {
            newRow = worksheet.createRow(destinationRowNum);
        }

        // Loop through source columns to add to new row
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            // Grab a copy of the old/new cell
            Cell oldCell = sourceRow.getCell(i);
            Cell newCell = newRow.createCell(i);

            // If the old cell is null jump to next cell
            if (oldCell == null) {
                newCell = null;
                continue;
            }

            // Copy style from old cell and apply to new cell
            CellStyle newCellStyle = workbook.createCellStyle();
            newCellStyle.cloneStyleFrom(oldCell.getCellStyle());
            ;
            newCell.setCellStyle(newCellStyle);

            // If there is a cell comment, copy
            if (oldCell.getCellComment() != null) {
                newCell.setCellComment(oldCell.getCellComment());
            }

            // If there is a cell hyperlink, copy
            if (oldCell.getHyperlink() != null) {
                newCell.setHyperlink(oldCell.getHyperlink());
            }

            // Set the cell data type
            newCell.setCellType(oldCell.getCellType());

            // Set the cell data value
            switch (oldCell.getCellType()) {
                case Cell.CELL_TYPE_BLANK:
                    newCell.setCellValue(oldCell.getStringCellValue());
                    break;
                case Cell.CELL_TYPE_BOOLEAN:
                    newCell.setCellValue(oldCell.getBooleanCellValue());
                    break;
                case Cell.CELL_TYPE_ERROR:
                    newCell.setCellErrorValue(oldCell.getErrorCellValue());
                    break;
                case Cell.CELL_TYPE_FORMULA:
                    newCell.setCellFormula(oldCell.getCellFormula());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                    newCell.setCellValue(oldCell.getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_STRING:
                    newCell.setCellValue(oldCell.getRichStringCellValue());
                    break;
            }
        }

        // If there are are any merged regions in the source row, copy to new row
        for (int i = 0; i < worksheet.getNumMergedRegions(); i++) {
            CellRangeAddress cellRangeAddress = worksheet.getMergedRegion(i);
            if (cellRangeAddress.getFirstRow() == sourceRow.getRowNum()) {
                CellRangeAddress newCellRangeAddress = new CellRangeAddress(newRow.getRowNum(),
                        (newRow.getRowNum() +
                                (cellRangeAddress.getLastRow() - cellRangeAddress.getFirstRow()
                                        )),
                        cellRangeAddress.getFirstColumn(),
                        cellRangeAddress.getLastColumn());
                worksheet.addMergedRegion(newCellRangeAddress);
            }
        }
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



package com.team6;


import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.Iterator;

public class WorkbookWriter {

	public static final int STARTING_ROW = 6;
	public static final int STARTING_CELL = 1;


	public static void writeAbsences(String teacherName, int month, int day, String period, String inputFile, String outputFile)
			throws InvalidFormatException, IOException {
		ArrayList<ArrayList<Cell>> content = readOnCallTally("./OnCall_Tallies_Example_edited.xlsx", 0);
		content = updateContent(teacherName, month, day, period, content);
		write(content, outputFile);
	}

	private static void write(ArrayList<ArrayList<Cell>> content, String outputFile) throws IOException {
		DataFormatter formatter = new DataFormatter();
		Workbook newgWorkbook = new XSSFWorkbook();
		Sheet sheet = newgWorkbook.createSheet();
		
		int rowCounter = 0;
		for(ArrayList<Cell> row : content) {
			Row aRow = sheet.createRow(rowCounter);
			int cellCounter = 0;
			for (Cell cell : row) {
				Cell newCell = aRow.createCell(cellCounter);
				
				XSSFCellStyle sourceCellStyle = (XSSFCellStyle) cell.getCellStyle();
				XSSFCellStyle clonedCellStyle = (XSSFCellStyle) newgWorkbook.createCellStyle();
				clonedCellStyle.cloneStyleFrom(sourceCellStyle);
				newCell.setCellStyle(clonedCellStyle);
				
				 CellType origCellType = cell.getCellTypeEnum();
				 newCell.setCellType(origCellType);  
                 
				switch (origCellType) {
                case BLANK:
                	newCell.setCellValue("");
                    break;

                case BOOLEAN:
                	newCell.setCellValue(cell.getBooleanCellValue());
                    break;

                case ERROR:
                	newCell.setCellErrorValue(cell.getErrorCellValue());
                    break;

                case FORMULA:
                	newCell.setCellFormula(cell.getCellFormula());
                    break;

                case NUMERIC:
                	newCell.setCellValue(cell.getNumericCellValue());
                    break;

                case STRING:
                	newCell.setCellValue(cell.getStringCellValue());
                    break;
                default:
                	newCell.setCellFormula(cell.getCellFormula());
                }

				newCell.setCellValue(formatter.formatCellValue(cell));
				cellCounter ++;
			}
			rowCounter ++;
		}
		
        FileOutputStream fileOut = new FileOutputStream(outputFile);
        newgWorkbook.write(fileOut);
        fileOut.close();

        newgWorkbook.close();
	}

	private static ArrayList<ArrayList<Cell>> updateContent(String teacherName, int month, int day, String period,
			ArrayList<ArrayList<Cell>> content) {

		DataFormatter formatter = new DataFormatter();
		
		String currentPeriod = "";
		for (int row = STARTING_ROW; row < content.size(); row++) {
			boolean rightRow = false;
			for (int cell = STARTING_CELL; cell < content.get(row).size(); cell++) {
				if (cell == STARTING_CELL) {
					if (formatter.formatCellValue(content.get(row).get(cell)).equals("Period 1")) {
						currentPeriod = "Period 1";
					} else if (formatter.formatCellValue(content.get(row).get(cell)).equals("Period 2")) {
						currentPeriod = "Period 2";
					} else if (formatter.formatCellValue(content.get(row).get(cell)).equals("Period 3")) {
						currentPeriod = "Period 3";
					} else if (formatter.formatCellValue(content.get(row).get(cell)).equals("Period 4")) {
						currentPeriod = "Period 4";
					}
				}
				if (formatter.formatCellValue(content.get(row).get(cell)).equals(teacherName)) {
					rightRow = true;
				} else if (currentPeriod.equals(period) && cell - 1 == day && rightRow == true) {
					Cell currentCell = content.get(row).get(cell);
					currentCell.setCellValue("1");
				}
			}
		}
		return content;
	}

	private static ArrayList<ArrayList<Cell>> readOnCallTally(String xlsFilePath, int workSheet)
			throws IOException, InvalidFormatException {
		
		Workbook workbook = WorkbookFactory.create(new File(xlsFilePath));

		Sheet sheet = workbook.getSheetAt(workSheet);


		ArrayList<ArrayList<Cell>> content = new ArrayList<ArrayList<Cell>>();
		int index = 0;
		content.add(new ArrayList<Cell>());

		Iterator<Row> rowIterator = sheet.rowIterator();
		while (rowIterator.hasNext()) {
			Row row = rowIterator.next();

			for (short i = 0; i <= row.getLastCellNum() - 1; i++) {

				Cell cell = row.getCell(i, org.apache.poi.ss.usermodel.Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
					content.get(index).add(cell);
			}
			content.add(new ArrayList<Cell>());
			index++;
		}
		return content;
	}
}

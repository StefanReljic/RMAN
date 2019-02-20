package utils;

import java.io.ByteArrayOutputStream;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public static final short HEADER_FONT_SIZE = 13;
	public static final float HEADER_COLUMN_WIDTH_MULTIPLIER = 1.5f;
	public static final String DOUBLE_CELL_FORMAT = "_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)";
	public static final String DEFAULT_SHEET_NAME = "sheet1";
	public static final String EXCEL_EXTENSION = ".xlsx";

	/**
	 * Returns byte representation of created excel.
	 * 
	 * @param valueMatrix
	 * @return
	 */
	public byte[] exportToByteArray(Object[][] valueMatrix) {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet(DEFAULT_SHEET_NAME);

			for (int i = 0; i < valueMatrix.length; i++) {
				Row row = sheet.createRow(i);

				if (i == 0) {
					row.setHeight((short) 1000);
				}

				for (int j = 0; j < valueMatrix[i].length; j++) {
					Cell cell = row.createCell(j);
					cell.setCellStyle(initCellStyle(workbook, i, j, valueMatrix.length, valueMatrix[i].length));

					if (valueMatrix[i][j] == null) {
						continue;
					}

					Double doubleValue = tryConvert(valueMatrix[i][j].toString());
					if (doubleValue != null) {
						cell.getCellStyle().setDataFormat(workbook.createDataFormat().getFormat(DOUBLE_CELL_FORMAT));
						cell.setCellValue(doubleValue);
					} else {
						cell.setCellValue(valueMatrix[i][j].toString());
					}
				}
			}

			if (valueMatrix.length > 0) {
				for (int i = 0; i < valueMatrix[0].length; i++) {
					sheet.autoSizeColumn(i);
					sheet.setColumnWidth(i, (int) (HEADER_COLUMN_WIDTH_MULTIPLIER * sheet.getColumnWidth(i)));
				}
			}

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			workbook.write(bos);

			return bos.toByteArray();

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	/***
	 * Return row index from cellId f.e. "E32" rowIndex = 32
	 * 
	 * @param cellId
	 * @return
	 */
	public int getRowIndex(String cellId) {

		int position = getCellSplitIndex(cellId);
		return Integer.parseInt(cellId.substring(position, cellId.length()));
	}

	/***
	 * Return columnIndex from cellId f.e. "B23" columnIndex = 2
	 * 
	 * @param cellId
	 * @return
	 */
	public int getColumnIndex(String cellId) {

		int position = getCellSplitIndex(cellId);
		String columnId = cellId.substring(0, position);
		int base = 26;
		char[] letters = columnId.toCharArray();
		int columnNumber = 0;
		for (int i = letters.length - 1; i >= 0; i--) {

			Integer charIntValue = letters[i] - 0x40;
			columnNumber += charIntValue * Math.pow(base, letters.length - i - 1);
		}
		return columnNumber;
	}

	/**
	 * Returns index of element that defines border between characters and numbers,
	 * f.e. "B32" returns 1.
	 * 
	 * @param cellId
	 * @return
	 */
	public int getCellSplitIndex(String cellId) {

		if (cellId == null)
			return 0;

		char[] chars = cellId.toCharArray();
		int index = 0;
		for (int i = 0; i < chars.length; ++i) {

			if (chars[i] < 'A' || chars[i] > 'Z') {
				index = i;
				break;
			}
		}
		return index;
	}

	private static Double tryConvert(String string) {
		try {
			return Double.parseDouble(string);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * Returns cell style.
	 * 
	 * @param workbook
	 * @param row
	 * @param col
	 * @param rowCount
	 * @param columnCount
	 * @return
	 */
	private XSSFCellStyle initCellStyle(XSSFWorkbook workbook, int row, int col, int rowCount, int columnCount) {
		XSSFCellStyle headerCellStyle = workbook.createCellStyle();
		if (row == 0) {
			Font headerFont = workbook.createFont();
			headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
			headerFont.setFontHeightInPoints(HEADER_FONT_SIZE);
			headerCellStyle.setFont(headerFont);

			headerCellStyle.setAlignment(HorizontalAlignment.CENTER);
			headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

			headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			headerCellStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		}

		headerCellStyle.setBorderTop(row == 0 ? BorderStyle.MEDIUM : BorderStyle.THIN);
		headerCellStyle.setBorderRight(col == columnCount - 1 ? BorderStyle.MEDIUM : BorderStyle.THIN);
		headerCellStyle.setBorderBottom((row == rowCount - 1) || row == 0 ? BorderStyle.MEDIUM : BorderStyle.THIN);
		headerCellStyle.setBorderLeft(col == 0 ? BorderStyle.MEDIUM : BorderStyle.THIN);

		return headerCellStyle;
	}
}
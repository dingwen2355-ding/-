package com.znv.manage.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.*;

@Slf4j
public class ExcelUtil {

	public static List<Map<String, Object>> resolveExcel(MultipartFile file, int beginColumn, int endColumn) {

		List<Map<String, Object>> resultList = new ArrayList<>();
		Workbook workbook = null;
		InputStream is = null;
		String titles[] = new String[endColumn - beginColumn];

		try {
			is = file.getInputStream();
			if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).equals(".xls")) {
				workbook = new HSSFWorkbook(is);
			} else if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
					.equals(".xlsx")) {
				workbook = new XSSFWorkbook(is);
			} else {
				return null;
			}

			Sheet sheet = workbook.getSheetAt(0);
			int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

			for (int i = 0; i < physicalNumberOfRows; i++) {
				Map<String, Object> mapInfo = new LinkedHashMap<>();
				if (i == 0) {
					for (int k = beginColumn; k < endColumn; k++) {
						if (sheet.getRow(i).getCell(k) != null) {
							Cell cell = sheet.getRow(i).getCell(k);
							cell.setCellType(cell.getCellType());
							titles[k] = String.valueOf(cell);
						} else {
							titles[k] = "";
						}
					}
					continue;
				} else {
					String str[] = new String[endColumn - beginColumn];

					for (int j = beginColumn; j < endColumn; j++) {
						Row row = sheet.getRow(i);
						if (row != null && row.getCell(j) != null) {
							Cell cell = sheet.getRow(i).getCell(j);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							str[j] = String.valueOf(cell);
						} else {
							str[j] = "";
						}
						mapInfo.put(titles[j], str[j]);
					}
				}

				resultList.add(mapInfo);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return resultList;
	}

	/**
	 *  添加多个Sheet页的excel解析
	 *   默认多个sheet页内容一致
	 * @param file
	 * @param beginColumn
	 * @param endColumn
	 * @return
	 */
	public static List<Map<String, Object>> resolveExcelWithSheet(MultipartFile file, int beginColumn, int endColumn) {

		Workbook workbook = null;
		InputStream is = null;
		String titles[] = new String[endColumn - beginColumn];

		try {
			List<Map<String, Object>> resultList = new ArrayList<>();
			is = file.getInputStream();
			if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")).equals(".xls")) {
				workbook = new HSSFWorkbook(is);
			} else if (file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."))
					.equals(".xlsx")) {
				workbook = new XSSFWorkbook(is);
			} else {
				return null;
			}

			for (int numSheet = 0; numSheet < workbook.getNumberOfSheets(); numSheet++) {
				Sheet sheet = workbook.getSheetAt(numSheet);
				if (sheet == null) {
					continue;
				}
				int physicalNumberOfRows = sheet.getPhysicalNumberOfRows();

				for (int i = 0; i < physicalNumberOfRows; i++) {
					Map<String, Object> mapInfo = new HashMap<>();
					if (i == 0) {
						for (int k = beginColumn; k < endColumn; k++) {
							if (sheet.getRow(i).getCell(k) != null) {
								Cell cell = sheet.getRow(i).getCell(k);
								cell.setCellType(cell.getCellType());
								titles[k] = String.valueOf(cell);
							} else {
								titles[k] = "";
							}
						}
					} else {
						String str[] = new String[endColumn - beginColumn];

						for (int j = beginColumn; j < endColumn; j++) {
							Row row = sheet.getRow(i);
							if (row != null && row.getCell(j) != null) {
								Cell cell = sheet.getRow(i).getCell(j);
								cell.setCellType(Cell.CELL_TYPE_STRING);
								str[j] = String.valueOf(cell);
							} else {
								str[j] = "";
							}
							mapInfo.put(titles[j], str[j]);
						}
						mapInfo.put("rowNo", i + 1);
						resultList.add(mapInfo);
					}
				}
			}
			return resultList;
		} catch (Exception e) {
			log.error(e.getMessage());
			return null;
		}

	}

	/**
	 * 将数据导出成Excel文件
	 *
	 * @param sheetName
	 *            sheet名称
	 * @param titles
	 *            标题
	 * @param values
	 *            内容
	 * @return
	 */
	public static HSSFWorkbook getHSSFWorkbook(int startRow, String sheetName, List<String> titles, String[][] values) {
		// 第一步，创建一个HSSFWorkbook，对应一个Excel文件
		HSSFWorkbook wb = new HSSFWorkbook();
		// 第二步，在workbook中添加一个sheet,对应Excel文件中的sheet
		HSSFSheet sheet = wb.createSheet(sheetName);
		// 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制
		HSSFRow row = sheet.createRow(startRow);
		// 第四步，创建单元格，并设置值表头 设置表头居中
		HSSFCellStyle style = wb.createCellStyle();
		// 创建一个居中格式
		style.setAlignment(CellStyle.ALIGN_CENTER);
		// 设置单元格宽度自适应，在此基础上把宽度调至1.5倍
		for (int i = 0; i < titles.size(); i++) {
			sheet.autoSizeColumn(i, true);
			sheet.setColumnWidth(i, sheet.getColumnWidth(i) * 15 / 10);
		}
		// 声明列对象
		HSSFCell cell = null;
		int width;
		// 创建标题
		for (int i = 0; i < titles.size(); i++) {
			cell = row.createCell(i);
			cell.setCellValue(titles.get(i));
			cell.setCellStyle(style);
			sheet.autoSizeColumn(i);
			width = sheet.getColumnWidth(i) * 15 / 10;
			if (width < 255 * 256) {
				sheet.setColumnWidth(i, width < 3000 ? 3000 : width);
			} else {
				sheet.setColumnWidth(i, 5000);
			}
		}
		// 创建内容
		HSSFFont font1 = wb.createFont();
		font1.setFontName("黑体");
		font1.setFontHeightInPoints((short) 10);//设置字体大小
		style.setFont(font1);
		for (int i = 0; i < values.length; i++) {
			row = sheet.createRow(i + 1 + startRow);
			for (int j = 0; j < values[i].length; j++) {
				// 将内容按顺序赋给对应的列对象
				cell = row.createCell(j);
				cell.setCellStyle(style);
				cell.setCellValue(values[i][j]);
			}
		}
		return wb;
	}
}

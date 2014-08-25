package com.sweroad.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.sweroad.model.Casualty;
import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;
import com.sweroad.service.CrashExcelService;

@Service("crashExcelService")
public class CrashExcelServiceImpl implements CrashExcelService {

	private Workbook workbook;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Workbook generateCrashExcelWorkBook(List<Crash> crashes) {
		workbook = new XSSFWorkbook();
		Sheet crashSheet = workbook.createSheet("Crash Data");
		Sheet vehicleSheet = workbook.createSheet("Vehicle Data");
		Sheet casualtySheet = workbook.createSheet("Casualty Data");
		createHeaderRowForCrashes(crashSheet);
		createHeaderRowForVehicles(vehicleSheet);
		createHeaderRowForCasualties(casualtySheet);
		int index = 1;
		for (Crash crash : crashes) {
			addCrashRow(crashSheet, crash, index++);
			Integer injuredDriverCount = 0;
			for (Vehicle vehicle : crash.getVehicles()) {
				addCrashVehicle(vehicleSheet, casualtySheet, vehicle, crash,
						injuredDriverCount);
			}
			int casualtyIndex = injuredDriverCount + 1;
			for (Casualty casualty : crash.getCasualties()) {
				addCrashCasualty(casualtySheet, casualty, crash,
						casualtyIndex++);
			}
		}
		autoSizeColumns();
		return workbook;
	}

	@Override
	public void generateAndWriteCrashExcelToFile(List<Crash> crashes,
			String filename) throws IOException {
		Workbook workbook = generateCrashExcelWorkBook(crashes);
		writeWorkBookToFile(workbook, filename);
	}

	private void createHeaderRowForCasualties(Sheet casualtySheet) {
		Row row = casualtySheet.createRow(0);
		int index = 0;
		addHeaderCell(row, index++, "No.");
		addHeaderCell(row, index++, "Crash TAR No.");
		addHeaderCell(row, index++, "Casualty Type");
		addHeaderCell(row, index++, "Casualty Sex");
		addHeaderCell(row, index++, "Casualty Age");
		addHeaderCell(row, index++, "Casualty Class");
		addHeaderCell(row, index++, "Vehicle No");
		addHeaderCell(row, index++, "Vehicle Type");
		addHeaderCell(row, index++, "Casualty Used Belt/Helmet");
	}

	private void createHeaderRowForVehicles(Sheet vehicleSheet) {
		Row row = vehicleSheet.createRow(0);
		int index = 0;
		addHeaderCell(row, index++, "No.");
		addHeaderCell(row, index++, "Crash TAR No.");
		addHeaderCell(row, index++, "Vehicle No.");
		addHeaderCell(row, index++, "Vehicle Type");
		addHeaderCell(row, index++, "Driver License Valid");
		addHeaderCell(row, index++, "Driver License No.");
		addHeaderCell(row, index++, "Driver Sex");
		addHeaderCell(row, index++, "Driver Age");
		addHeaderCell(row, index++, "Driver Used Belt");
		addHeaderCell(row, index++, "Driver Casualty");
		addHeaderCell(row, index++, "Company Name");
	}

	private void createHeaderRowForCrashes(Sheet crashSheet) {
		Row row = crashSheet.createRow(0);
		int index = 0;
		addHeaderCell(row, index++, "No.");
		addHeaderCell(row, index++, "TAR No.");
		addHeaderCell(row, index++, "Police Station");
		addHeaderCell(row, index++, "District");
		addHeaderCell(row, index++, "Town/Village");
		addHeaderCell(row, index++, "Road");
		addHeaderCell(row, index++, "Road Number");
		addHeaderCell(row, index++, "Crash Place");
		addHeaderCell(row, index++, "Crash Date & Time");
		addHeaderCell(row, index++, "Year");
		addHeaderCell(row, index++, "Month");
		addHeaderCell(row, index++, "Day");
		addHeaderCell(row, index++, "Time");
		addHeaderCell(row, index++, "Day of Week");
		addHeaderCell(row, index++, "Latitude");
		addHeaderCell(row, index++, "Longitude");
		addHeaderCell(row, index++, "Crash Severity");
		addHeaderCell(row, index++, "Collision Type");
		addHeaderCell(row, index++, "Main Cause of Crash");
		addHeaderCell(row, index++, "Vehicle Failure Contributing to Crash");
		addHeaderCell(row, index++, "Weather");
		addHeaderCell(row, index++, "Surface Condition");
		addHeaderCell(row, index++, "Road Surface");
		addHeaderCell(row, index++, "Surface Type");
		addHeaderCell(row, index++, "Roadway Character");
		addHeaderCell(row, index++, "Junction Type");
	}

	private void addHeaderCell(Row row, int index, String cellValue) {
		Cell cell = row.createCell(index, Cell.CELL_TYPE_STRING);
		cell.setCellValue(cellValue);
		cell.getCellStyle().setAlignment(CellStyle.ALIGN_CENTER);
		Font font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 14);
		cell.getCellStyle().setFont(font);
		cell.getCellStyle().setBorderTop(CellStyle.BORDER_THIN);
		cell.getCellStyle().setBorderLeft(CellStyle.BORDER_THIN);
		cell.getCellStyle().setBorderRight(CellStyle.BORDER_THIN);
		cell.getCellStyle().setBorderBottom(CellStyle.BORDER_THIN);
	}

	private void autoSizeColumns() {
		for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
			Sheet sheet = workbook.getSheetAt(i);
			for (int j = 0; j < sheet.getRow(0).getPhysicalNumberOfCells(); j++) {
				sheet.autoSizeColumn(j);
			}
		}
	}

	private void addCrashRow(Sheet crashSheet, Crash crash, Integer index) {
		Row row = crashSheet.createRow(index);
		int cellIndex = 0;
		addRowCell(row, cellIndex++, index.toString(), Cell.CELL_TYPE_NUMERIC,
				getRightAlignStyle());
		addRowCell(row, cellIndex++, crash.getTarNo(), Cell.CELL_TYPE_STRING,
				null);
		addRowCell(row, cellIndex++, crash.getPoliceStation() != null ? crash
				.getPoliceStation().getName() : "", Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getPoliceStation() != null ? crash
				.getPoliceStation().getDistrict().getName() : "",
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getTownOrVillage(),
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getRoad(), Cell.CELL_TYPE_STRING,
				null);
		addRowCell(row, cellIndex++, crash.getRoadNumber(),
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getCrashPlace(),
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getCrashDisplayDate(),
				Cell.CELL_TYPE_STRING, getRightAlignStyle());
		addRowCell(row, cellIndex++, crash.getCrashYear() != null ? crash
				.getCrashYear().toString() : "", Cell.CELL_TYPE_NUMERIC, null);
		addRowCell(row, cellIndex++, crash.getCrashMonth() != null ? crash
				.getCrashMonth().toString() : "", Cell.CELL_TYPE_NUMERIC, null);
		addRowCell(row, cellIndex++, crash.getCrashDayOfMonth() != null ? crash
				.getCrashDayOfMonth().toString() : "", Cell.CELL_TYPE_NUMERIC,
				null);
		addRowCell(row, cellIndex++, crash.getCrashTime(),
				Cell.CELL_TYPE_STRING, getCenterAlignStyle());
		addRowCell(row, cellIndex++, crash.getCrashDayOfWeek(),
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getGpsCoordinate() != null ? crash
				.getGpsCoordinate().getLatitude() : "", Cell.CELL_TYPE_STRING,
				null);
		addRowCell(row, cellIndex++, crash.getGpsCoordinate() != null ? crash
				.getGpsCoordinate().getLongitude() : "", Cell.CELL_TYPE_STRING,
				null);
		addRowCell(row, cellIndex++, crash.getCrashSeverity() != null ? crash
				.getCrashSeverity().getName() : "", Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getCollisionType() != null ? crash
				.getCollisionType().getName() : "", Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getMainCrashCause() != null ? crash
				.getMainCrashCause().getName() : "", Cell.CELL_TYPE_STRING,
				null);
		addRowCell(row, cellIndex++,
				crash.getVehicleFailureType() != null ? crash
						.getVehicleFailureType().getName() : "",
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getWeather() != null ? crash
				.getWeather().getName() : "", Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++,
				crash.getSurfaceCondition() != null ? crash
						.getSurfaceCondition().getName() : "",
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getRoadSurface() != null ? crash
				.getRoadSurface().getName() : "", Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getSurfaceType() != null ? crash
				.getSurfaceType().getName() : "", Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++,
				crash.getRoadwayCharacter() != null ? crash
						.getRoadwayCharacter().getName() : "",
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, crash.getJunctionType() != null ? crash
				.getJunctionType().getName() : "", Cell.CELL_TYPE_STRING, null);
	}

	private void addRowCell(Row row, int cellIndex, String value, int cellType,
			CellStyle cellStyle) {
		Cell cell = row.createCell(cellIndex++, cellType);
		if (cellType == Cell.CELL_TYPE_NUMERIC && !"".equals(value.trim())) {
			cell.setCellValue(Double.parseDouble(value));
		} else {
			cell.setCellValue(value);
		}
		if (cellStyle == null) {
			cellStyle = workbook.createCellStyle();
		}
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cell.setCellStyle(cellStyle);
	}

	private void addCrashVehicle(Sheet vehicleSheet, Sheet casualtySheet,
			Vehicle vehicle, Crash crash, Integer injuredDriverCount) {
		Integer index = vehicleSheet.getPhysicalNumberOfRows();
		Row row = vehicleSheet.createRow(index);
		int cellIndex = 0;
		addRowCell(row, cellIndex++, index.toString(), Cell.CELL_TYPE_NUMERIC,
				getRightAlignStyle());
		addRowCell(row, cellIndex++, crash.getTarNo(), Cell.CELL_TYPE_STRING,
				null);
		addRowCell(row, cellIndex++, vehicle.getNumber() != null ? vehicle
				.getNumber().toString() : "", Cell.CELL_TYPE_NUMERIC,
				getRightAlignStyle());
		addRowCell(row, cellIndex++, vehicle.getVehicleType() != null ? vehicle
				.getVehicleType().getName() : "", Cell.CELL_TYPE_STRING, null);
		if (vehicle.getDriver() != null) {
			addRowCell(row, cellIndex++,
					vehicle.getDriver().getLicenseValid() != null ? vehicle
							.getDriver().getLicenseValid() ? "Yes" : "No"
							: "Uknown", Cell.CELL_TYPE_STRING, null);
			addRowCell(row, cellIndex++,
					vehicle.getDriver().getLicenseNumber(),
					Cell.CELL_TYPE_STRING, null);
			addRowCell(row, cellIndex++, vehicle.getDriver().getGender(),
					Cell.CELL_TYPE_STRING, getCenterAlignStyle());
			addRowCell(row, cellIndex++,
					vehicle.getDriver().getAge() != null ? vehicle.getDriver()
							.getAge().toString() : "", Cell.CELL_TYPE_NUMERIC,
					getRightAlignStyle());
			addRowCell(row, cellIndex++,
					vehicle.getDriver().getBeltUsed() != null ? vehicle
							.getDriver().getBeltUsed() ? "Yes" : "No"
							: "Uknown", Cell.CELL_TYPE_STRING, null);
			addRowCell(row, cellIndex++,
					vehicle.getDriver().getCasualtyType() != null ? vehicle
							.getDriver().getCasualtyType().getName() : "",
					Cell.CELL_TYPE_STRING, null);
			addCasualtyVehicleDriver(casualtySheet, vehicle, crash,
					injuredDriverCount);
		} else {
			int i = 0;
			while (i < 6) {
				addRowCell(row, cellIndex++, "", Cell.CELL_TYPE_STRING, null);
				i++;
			}
		}
		addRowCell(row, cellIndex++, vehicle.getCompanyName(),
				Cell.CELL_TYPE_STRING, null);
	}

	private void addCrashCasualty(Sheet casualtySheet, Casualty casualty,
			Crash crash, Integer casualtyNum) {
		Integer index = casualtySheet.getPhysicalNumberOfRows();
		Row row = casualtySheet.createRow(index);
		int cellIndex = 0;
		addRowCell(row, cellIndex++, index.toString(), Cell.CELL_TYPE_NUMERIC,
				getRightAlignStyle());
		addRowCell(row, cellIndex++, crash.getTarNo(), Cell.CELL_TYPE_STRING,
				null);
		addRowCell(row, cellIndex++,
				casualty.getCasualtyType() != null ? casualty.getCasualtyType()
						.getName() : "", Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, casualty.getGender(),
				Cell.CELL_TYPE_STRING, getCenterAlignStyle());
		addRowCell(row, cellIndex++, casualty.getAge() != null ? casualty
				.getAge().toString() : "", Cell.CELL_TYPE_NUMERIC,
				getRightAlignStyle());
		addRowCell(row, cellIndex++,
				casualty.getCasualtyClass() != null ? casualty
						.getCasualtyClass().getName() : "",
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, casualty.getVehicle() != null ? "Vehile "
				+ casualty.getVehicle().getNumber() : "",
				Cell.CELL_TYPE_STRING, null);
		addRowCell(row, cellIndex++, casualty.getVehicle() != null
				&& casualty.getVehicle().getVehicleType() != null ? casualty
				.getVehicle().getVehicleType().getName() : "",
				Cell.CELL_TYPE_STRING, null);
		addRowCell(
				row,
				cellIndex++,
				casualty.getBeltOrHelmetUsed() != null ? casualty
						.getBeltOrHelmetUsed() ? "Yes" : "No" : "Uknown",
				Cell.CELL_TYPE_STRING, null);
	}

	private void addCasualtyVehicleDriver(Sheet casualtySheet, Vehicle vehicle,
			Crash crash, Integer injuredDriverCount) {
		Casualty casualty = vehicle.getDriver().toCasualty(vehicle);
		if (casualty != null) {
			injuredDriverCount++;
			addCrashCasualty(casualtySheet, casualty, crash, injuredDriverCount);
		}
	}

	private void writeWorkBookToFile(Workbook workbook, String fileName)
			throws IOException {
		FileOutputStream out = new FileOutputStream(new File(fileName));
		workbook.write(out);
		out.close();
	}

	private CellStyle getRightAlignStyle() {
		CellStyle styleRightAlign = workbook.createCellStyle();
		styleRightAlign.setAlignment(CellStyle.ALIGN_RIGHT);
		return styleRightAlign;
	}

	private CellStyle getCenterAlignStyle() {
		CellStyle styleRightAlign = workbook.createCellStyle();
		styleRightAlign.setAlignment(CellStyle.ALIGN_CENTER);
		return styleRightAlign;
	}
}

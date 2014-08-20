package com.sweroad.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.sweroad.model.Casualty;
import com.sweroad.model.Crash;
import com.sweroad.model.Vehicle;

public class CrashExcelServiceTest extends BaseManagerTestCase {

	@Autowired
	private CrashExcelService crashExcelService;
	private List<Crash> crashes;

	@Before
	public void setUp() {
		crashes = new ArrayList<Crash>();
		addCrashToList(2, 1);
		addCrashToList(1, 2);
		addCrashToList(10, 3);
		addCrashToList(0, 1);
		addCrashToList(1, 1);
	}

	private void addCrashToList(int numCasualties, int numVehicles) {
		int index = crashes.size();
		crashes.add(new Crash());
		int i = 0;
		while (i < numCasualties) {
			crashes.get(index).addCasualty(new Casualty());
			i++;
		}
		i = 0;
		while (i < numVehicles) {
			crashes.get(index).addVehicle(new Vehicle());
			i++;
		}
	}

	@Test
	public void testThatGenerateExcelWorkbookDoesntReturnNull() {
		assertNotNull(crashExcelService.generateCrashExcelWorkBook(crashes));
	}

	@Test
	public void testThatGeneratedExcelWorkbookHas3Worksheets() {
		Workbook workbook = crashExcelService
				.generateCrashExcelWorkBook(crashes);
		assertEquals(3, workbook.getNumberOfSheets());
	}

	@Test
	public void testThat1stSheetOfGeneratedWorkbookHasCorrectNumberOfRows() {
		Workbook workbook = crashExcelService
				.generateCrashExcelWorkBook(crashes);
		assertEquals(6, workbook.getSheetAt(0).getPhysicalNumberOfRows());
	}
	
	@Test
	public void testThat2ndSheetOfGeneratedWorkbookHasCorrectNumberOfRows() {
		Workbook workbook = crashExcelService
				.generateCrashExcelWorkBook(crashes);
		assertEquals(9, workbook.getSheetAt(1).getPhysicalNumberOfRows());
	}
	
	@Test
	public void testThat3rdSheetOfGeneratedWorkbookHasCorrectNumberOfRows() {
		Workbook workbook = crashExcelService
				.generateCrashExcelWorkBook(crashes);
		assertEquals(15, workbook.getSheetAt(2).getPhysicalNumberOfRows());
	}
}

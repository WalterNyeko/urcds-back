package com.sweroad.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import com.sweroad.model.Crash;

public interface CrashExcelService {

	/**
	 * Generates Excel file containing crash data and returns filename.
	 * 
	 * @param crashes
	 *            List of crashes from db
	 * @return File name of excel file
	 * @throws IOException
	 *             Throw in case of read/write failure
	 */
	@Transactional
	String generateCrashExcelFile(List<Crash> crashes) throws IOException;

	/**
	 * Generates Excel Workbook containing crash data
	 * @param crashes List of crashes from db
	 * @return Excel Workbook
	 */
	Workbook generateCrashExcelWorkBook(List<Crash> crashes);
}

package com.sweroad.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.transaction.annotation.Transactional;

import com.sweroad.model.Crash;

public interface CrashExcelService {

	/**
	 * Generates Excel Workbook containing crash data
	 * 
	 * @param crashes
	 *            List of crashes from db
	 * @return Excel Workbook
	 */
	@Transactional
	Workbook generateCrashExcelWorkBook(List<Crash> crashes);

	/***
	 * Generates Excel Workbook containing crash data and writes it to file
	 * 
	 * @param crashes
	 *            List of crashes from db
	 * @param filename
	 *            File name (with full path) to save excel under
	 * @throws IOException
	 *             Throw in case of read/write failure
	 */
	@Transactional
	void generateAndWriteCrashExcelToFile(List<Crash> crashes, String filename)
			throws IOException;
}

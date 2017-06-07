package com.vistalytics.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class JsonWriter {
	private static Logger logger = Logger.getLogger(JsonWriter.class);

	/**
	 * Method to write given jsonData to file
	 * 
	 * @param jsonData
	 * @param jsonFile
	 */
	public static void writeData(String jsonData, File jsonFile) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(jsonFile);
			fileWriter.write(jsonData);
		} catch (Throwable e) {
			logger.error("Error while writing json to file " + e.getMessage());
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				logger.error("Error while closing file " + e.getMessage());
			}
		}
	}
}

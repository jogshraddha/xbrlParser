package com.vistalytics.xbrlParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.log4j.Logger;

public class JsonWriter {
	
	private Logger logger = Logger.getLogger(JsonWriter.class);
	/**
	 * Method to write given jsonData to file
	 * @param jsonData
	 * @param jsonFile
	 */
	public static void writeData(String jsonData, File jsonFile) {
		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(jsonFile);
			fileWriter.write(jsonData);
			// System.out.println(gson.toJson(jsonObject));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
			} catch (IOException e) {
				
			}
		}
	}
}

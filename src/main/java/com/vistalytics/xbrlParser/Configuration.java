package com.vistalytics.xbrlParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	private static final String CONFIG_FILE_PATH = "Configuration.properties";
	private static final String XBRL_DIR_PATH = "xbrlDirectoryPath";
	private static final String JSON_DIR_PATH = "jsonDirectoryPath";

	public static String xbrlDirectoryPath = null;
	public static String jsonDirectoryPath = null;

	public static void loadCoreConfiguration() throws IOException {
		try {
			System.out.println("Loaded configuration for the project");
			InputStream is = getConfigFilePath();
			Properties props = new Properties();
			if (is != null) {
				try {
					props.load(is);
				} finally {
					is.close();
				}
			}
			try {
				xbrlDirectoryPath = props.getProperty(XBRL_DIR_PATH);
				jsonDirectoryPath = props.getProperty(JSON_DIR_PATH);
				System.out.println(xbrlDirectoryPath);
				System.out.println(jsonDirectoryPath);
			} catch (NumberFormatException nfe) {
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static InputStream getConfigFilePath() {
		InputStream webConfigPath = Configuration.class.getClassLoader()
				.getResourceAsStream(CONFIG_FILE_PATH);
		return webConfigPath;
	}

}

package com.vistalytics.xbrlParser;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import jeasyxbrl.taxonomy.instance.XbrlInstance;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class XbrlConverter {

	private Logger logger = Logger.getLogger(XbrlConverter.class);
	
	private static String JSON_EXTENSION = ".json";
	private static String[] EXTENSIONS = new String[] { "xml" };
	private static String XBRL_RESOURCE_NAME = "XBRL";
	private static String JSON_RESOURCE_NAME = "JSON";

	private static String EXTENSION_CAL = "cal";
	private static String EXTENSION_PRE = "pre";
	private static String EXTENSION_LAB = "lab";
	private static String EXTENSION_DEF = "def";

	private static final String JSON_DIR_PATH = App.class.getClassLoader()
			.getResource(JSON_RESOURCE_NAME).getPath();
	private static final String XBRL_DIR_PATH = App.class.getClassLoader()
			.getResource(XBRL_RESOURCE_NAME).getPath();

	/**
	 * Method to retrieve all xbrl document and convert to json
	 */
	public void getXbrlJson() {
		File directory = new File(XBRL_DIR_PATH);
		if (directory.exists()) {
			File[] subDirectories = directory.listFiles(getDirFilter());
			for (File file : subDirectories) {
				String json = generateJsonFromXbrl(file);
				StringBuilder jsonPathBuilder = new StringBuilder();
				jsonPathBuilder.append(JSON_DIR_PATH).append(File.separator)
						.append(file.getName()).append(JSON_EXTENSION);
				File jsonFile = new File(jsonPathBuilder.toString());
				JsonWriter.writeData(json, jsonFile);
			}
		}
	}

	/**
	 * Method to generate JSON data for the given XBRL file
	 * @param file
	 * @return String
	 */
	private String generateJsonFromXbrl(File file) {
		String json = null;
		if (file.isDirectory()) {
			ArrayList<String> labelList = new ArrayList<String>();
			ArrayList<String> calList = new ArrayList<String>();
			ArrayList<String> defList = new ArrayList<String>();
			ArrayList<String> preList = new ArrayList<String>();
			ArrayList<String> instanceList = new ArrayList<String>();
			@SuppressWarnings("unchecked")
			List<File> files = (List<File>) FileUtils.listFiles(file,
					EXTENSIONS, true);
			for (File xmlFile : files) {
				String fileName = xmlFile.getName();
				StringBuilder xmlFilePath = new StringBuilder();
				xmlFilePath.append(file.getAbsolutePath())
						.append(File.separator).append(fileName);
				if (fileName.contains(EXTENSION_LAB)) {
					// Adding native XBRL linkbase file: my_dir/file
					labelList.add(xmlFilePath.toString());
				} else if (fileName.contains(EXTENSION_DEF)) {
					defList.add(xmlFilePath.toString());
				} else if (fileName.contains(EXTENSION_PRE)) {
					preList.add(xmlFilePath.toString());
				} else if (fileName.contains(EXTENSION_CAL)) {
					calList.add(xmlFilePath.toString());
				} else {
					instanceList.add(xmlFilePath.toString());
				}
			}
			// loading Financial report to in-memory Java Class
			json = convertXbrlToJson(instanceList, labelList, calList, defList,
					preList);
		}
		return json;
	}

	/**
	 * Method to convert given XBRL to JSON
	 * @param instanceNameList
	 * @param labelNameList
	 * @param calculationNameList
	 * @param definitionNameList
	 * @param presentationNameList
	 * @return String
	 */
	public static String convertXbrlToJson(ArrayList<String> instanceNameList,
			ArrayList<String> labelNameList,
			ArrayList<String> calculationNameList,
			ArrayList<String> definitionNameList,
			ArrayList<String> presentationNameList) {
		jeasyxbrl.JeasyXbrl jxbrl = new jeasyxbrl.JeasyXbrl();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonObject jsonObject = new JsonObject();
		try {
			jxbrl.loadingInstancesInCache(instanceNameList, labelNameList,
					calculationNameList, null, presentationNameList,
					definitionNameList, null, 1);
			ArrayList<XbrlInstance> xbrlList = jxbrl
					.getXbrlInstanceListByCache();
			// From in-memory Java Class to Json
			String xjson = gson.toJson(xbrlList);
			JsonElement jsonElement = new JsonParser().parse(xjson);
			jsonObject.add("root", jsonElement);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return gson.toJson(jsonObject);
	}

	/**
	 * FileFilter to check if the given file is a directory
	 * @return FileFilter
	 */
	private FileFilter getDirFilter() {
		return new FileFilter() {
			public boolean accept(File file) {
				return file.isDirectory();
			}
		};
	}

}

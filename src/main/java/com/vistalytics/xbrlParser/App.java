package com.vistalytics.xbrlParser;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.vistalytics.xbrlParser.Configuration;
import com.vistalytics.util.XbrlConversionUtil;

/**
 * Hello world!
 *
 */
public class App 
{
	private Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	try {
			Configuration.loadCoreConfiguration();
			new XbrlConversionUtil().convertXbrl();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

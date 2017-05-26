package com.vistalytics.xbrlParser;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
        new XbrlConverter().getXbrlJson();
    }
}

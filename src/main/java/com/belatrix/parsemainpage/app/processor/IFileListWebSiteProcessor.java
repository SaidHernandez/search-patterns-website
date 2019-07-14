package com.belatrix.parsemainpage.app.processor;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belatrix.parsemainpage.app.business.ISearchPartternsBusiness;

/**
 * 
 * @author SAID HERNANDEZ
 *
 */
public interface IFileListWebSiteProcessor {
	
	/** application log */
	public static final Logger logger = LogManager.getLogger(ISearchPartternsBusiness.class);
	
	/**
	 * Read file locally and return web site lists
	 * @return List<String>
	 */
	public List<String> readFileLocal(); 
	
	
	/**
	 * Generating report with HashTag finds without web site list 
	 * @param mapHashTag
	 * @return
	 */
	public boolean reportHashTagFile(Map<String, List<String>> mapHashTag); 

}

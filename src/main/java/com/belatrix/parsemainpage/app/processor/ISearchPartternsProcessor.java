package com.belatrix.parsemainpage.app.processor;

import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.belatrix.parsemainpage.app.business.ISearchPartternsBusiness;

public interface ISearchPartternsProcessor {
	
	/** application log */
	public static final Logger logger = LogManager.getLogger(ISearchPartternsBusiness.class);
	
	/**
	 * 
	 * @param urlsWebSite
	 * @return
	 */
	public Map<String, List<String>> findPatterns(List<String> urlsWebSite) ;

}

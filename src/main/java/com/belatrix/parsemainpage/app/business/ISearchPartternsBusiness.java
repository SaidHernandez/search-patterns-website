package com.belatrix.parsemainpage.app.business;



import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.belatrix.parsemainpage.app.processor.ISearchPartternsProcessor;
/**
 * Business layer responsible for orchestrating the processors
 * 
 * @author SAID HERNANDEZ
 *
 */
@Component
public interface ISearchPartternsBusiness {
	
	/** application log */
	public static final Logger logger = LogManager.getLogger(ISearchPartternsBusiness.class);
	
	/**
	 * Finding the patterns without website.
	 * @param iSearchPartternsProcessor
	 */
	public void findPatterns(ISearchPartternsProcessor iSearchPartternsProcessor, HttpServletResponse response) ;

}

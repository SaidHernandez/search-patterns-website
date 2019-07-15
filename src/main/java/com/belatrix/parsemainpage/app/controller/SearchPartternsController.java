package com.belatrix.parsemainpage.app.controller;

import java.io.IOException;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.belatrix.parsemainpage.app.business.ISearchPartternsBusiness;
import com.belatrix.parsemainpage.app.modelo.SearchPartternRs;
import com.belatrix.parsemainpage.app.modelo.SearchPatternsRq;
import com.belatrix.parsemainpage.app.processor.ISearchPartternsProcessor;

/**
 * 
 * @author SAID HERNANDEZ
 *
 */
@Controller
@RequestMapping(value = "/app")
public class SearchPartternsController {
	
	/** application log */
	private static final Logger logger = LogManager.getLogger(SearchPartternsController.class);
	
	@Autowired
	@Qualifier("searchPartternsBusiness")
	ISearchPartternsBusiness searchPartternsBusiness ; 
	
	@Autowired
	@Qualifier("partternsHashTag")
	ISearchPartternsProcessor searchPatternsProcessor; 

	@RequestMapping(value={"","/", "/app"},  produces="application/zip")
	public String init(HttpServletResponse response) {
		logger.info("Incio del metodo init");
		searchPartternsBusiness.findPatterns(searchPatternsProcessor, response);
		return "index";
	}

}

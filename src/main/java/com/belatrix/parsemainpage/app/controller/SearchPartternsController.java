package com.belatrix.parsemainpage.app.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.belatrix.parsemainpage.app.business.ISearchPartternsBusiness;
import com.belatrix.parsemainpage.app.modelo.SearchPartternRs;
import com.belatrix.parsemainpage.app.modelo.SearchPatternsRq;
import com.belatrix.parsemainpage.app.processor.IFileListWebSiteProcessor;
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

	@RequestMapping(value={"","/", "/app"}, method= RequestMethod.GET)
	public String init(Model model) {
		logger.info("Incio del metodo init - pantalla incial");
		searchPartternsBusiness.findPatterns(searchPatternsProcessor);
		return "index"; 
	}
	
	@RequestMapping(value = "/search-patterns", method= RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
	public SearchPatternsRq searchPatterns(@Valid SearchPartternRs patternsRs) {
		logger.info("Incio del metodo searchPatterns - pantalla incial");
		SearchPatternsRq patternsRq =  new SearchPatternsRq();	
		return patternsRq;
	}
	
	
}

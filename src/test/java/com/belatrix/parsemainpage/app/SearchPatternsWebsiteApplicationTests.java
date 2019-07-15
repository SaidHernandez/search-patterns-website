package com.belatrix.parsemainpage.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.belatrix.parsemainpage.app.business.ISearchPartternsBusiness;
import com.belatrix.parsemainpage.app.processor.IFileListWebSiteProcessor;
import com.belatrix.parsemainpage.app.processor.ISearchPartternsProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchPatternsWebsiteApplicationTests {
	
	@Autowired
	IFileListWebSiteProcessor fileListWebSiteProcessor;
	
	@Autowired
	@Qualifier("partternsHashTag")
	ISearchPartternsProcessor searchPatternsProcessor; 

	@Test
	public void contextLoads() {
		fileListWebSiteProcessor.readFileLocal();
		
	}
	
	@Test
	public void readFileTest() {
		assertNotEquals("Error al leer la lista de WebSite", 0, fileListWebSiteProcessor.readFileLocal().size());
	}
	
	@Test
	public void searchPatternsTest() {
		List<String> listTest = fileListWebSiteProcessor.readFileLocal();
		assertNotEquals("Error al buscar hashtag", 0, searchPatternsProcessor.findPatterns(listTest).size());
	}

}

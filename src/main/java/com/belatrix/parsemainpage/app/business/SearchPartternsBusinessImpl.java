package com.belatrix.parsemainpage.app.business;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.belatrix.parsemainpage.app.processor.IFileListWebSiteProcessor;
import com.belatrix.parsemainpage.app.processor.ISearchPartternsProcessor;


@Component("searchPartternsBusiness")
public class SearchPartternsBusinessImpl implements ISearchPartternsBusiness {
	
	@Autowired
	IFileListWebSiteProcessor fileListWebSiteProcessor;
    
	/*
	 * (non-Javadoc)
	 * @see com.belatrix.parsemainpage.app.business.ISearchPartternsBusiness#findPatterns(com.belatrix.parsemainpage.app.processor.ISearchPartternsProcessor)
	 */
	@Override
	public void findPatterns(ISearchPartternsProcessor iSearchPartternsProcessor) {
		try {
			logger.info("Inicio del metodo - findPatterns");
			
			List<String> urlsWebSite =  fileListWebSiteProcessor.readFileLocal();
			logger.info("Se cargan el listado de website desde el archivo local, cantidad de URL: " + urlsWebSite.size());
			
			Map<String, List<String>> mapHashTag = iSearchPartternsProcessor.findPatterns(urlsWebSite);
			logger.info("Se procesa las url y se relacionan en el map: " + mapHashTag.size());
			
			boolean response = fileListWebSiteProcessor.reportHashTagFile(mapHashTag);
			logger.info("Se genera el reporte en sus respetivos archivos: " + response );
		} catch (Exception e) {
			logger.error("Error presentado en la capa de negocio: " + e.getMessage());
		}
	}
}

package com.belatrix.parsemainpage.app.processor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

/**
 * 
 * @author SAID HERNANDEZ
 *
 */
@Component("partternsHashTag")
public class SearchHashTagProcessorImpl  implements ISearchPartternsProcessor{
	
	Map<String, List<String>> resultsHastTag = new HashMap<String, List<String>>();
    
	/*
	 * (non-Javadoc)
	 * @see com.belatrix.parsemainpage.app.processor.ISearchPartternsProcessor#findPatterns(java.util.List)
	 */
	@Override
	public Map<String, List<String>> findPatterns(List<String> urlsWebSite) {
		logger.info("Inicio del metodo findPatterns");
		List<Callable<Map<String, List<String>>>> callables = new ArrayList<Callable<Map<String, List<String>>>>();
		ExecutorService executorService = Executors.newWorkStealingPool();
		
		logger.debug("Inicializacion de variables correctamente");

		for (String urlWebSite : urlsWebSite) {
			logger.info("Se proceden a crear todos los Callable para palerilizar las tareas");
			Callable<Map<String, List<String>>> task = () -> {
				Map<String, List<String>> resultHastTag = new HashMap<String, List<String>>();
				String pageTitle = "";
				LinkedHashSet<String> hashSet;
				try {
					List<String> listHastTag = new ArrayList<>();
					String name = Thread.currentThread().getName();
					logger.debug("Nombre del hijo en ejecucion " + name);

					Document document = Jsoup.connect(urlWebSite).userAgent("Mozilla").get();
					Elements links = document.getAllElements();
					pageTitle = document.baseUri();
					pageTitle = pageTitle.substring(pageTitle.indexOf(".") + 1, pageTitle.lastIndexOf(".") - 1);
					
					logger.debug("nombre de la pagina escaneada" + pageTitle);
					List<Element> linkFinal = links.stream().filter(x -> x.text().contains("#"))
							.collect(Collectors.toList());
					for (Element link : linkFinal) {
						logger.debug("Informacion del texto a procesar: " + link.text());
						listHastTag.addAll(Arrays.stream(link.text().split(" ")).filter(h -> h.startsWith("#") == true)
								.collect(Collectors.toList()));
					}
					hashSet = new LinkedHashSet<>(listHastTag);
					listHastTag = new ArrayList<>(hashSet);
					resultHastTag.put(pageTitle, listHastTag);
				} catch (IOException e) {
					logger.error("Error con URL " + urlWebSite + " casua: " + e.getMessage());
				}
				return resultHastTag;
			};
			callables.add(task);
		}

		try {
			logger.info("Se proceden a ejecutar el pool de callable");
			List<Future<Map<String, List<String>>>> futures = executorService.invokeAll(callables);
			futures.forEach(x -> {
				try {
					if (x.get(10, TimeUnit.SECONDS) != null) {
						for (Map.Entry<String, List<String>> entry : x.get().entrySet()) {
							logger.debug(" clave: " + entry.getKey() + " Valor: " + entry.getValue());
							resultsHastTag.put(entry.getKey(), entry.getValue());
						}
					}

				} catch (InterruptedException e) {
					logger.error("Error presentado en tomar el resultado del callable" + e.getMessage());
				} catch (ExecutionException e) {
					logger.error("Error presentado en tomar el resultado del callable" + e.getMessage());
				} catch (TimeoutException e) {
					logger.error("Error presentado tiempo fuera en tomar el resultado del callable" + e.getMessage());
				}
			});
		} catch (InterruptedException e) {
			logger.error("Error presentado en la ejecucion del pool" + e.getMessage());
		} finally {
			executorService.shutdownNow();
		}
		
		logger.info("Se buscan los hash tag correctamente");
		return resultsHastTag;
	}

}

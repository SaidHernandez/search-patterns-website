package com.belatrix.parsemainpage.app.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.belatrix.parsemainpage.app.controller.SearchPartternsController;

/**
 * Log handling for processor file
 * @author SAID HERNANDEZ
 *
 */
@Component
public class FileListWebSiteProcessorImpl implements IFileListWebSiteProcessor {
  
	private final String PATH_BASE = "C:\\Users\\SAID HERNANDEZ\\Documents\\Belatrix\\reportes";
	
	@Value("classpath:SetListDefault.txt")
	Resource resourceFile;
    
	/*
	 * (non-Javadoc)
	 * @see com.belatrix.parsemainpage.app.processor.IFileListWebSiteProcessor#readFileLocal()
	 */
	@Override
	public List<String> readFileLocal() {
		logger.info("Inicio del metodo - readFileLocal");
		List<String> listWebSiteDefault = new ArrayList<>();
		try (InputStream in = resourceFile.getInputStream();
				BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				listWebSiteDefault.add(line);
				logger.debug("Linea procesadas" + line);
			}
			logger.info("Se procesa el archivo correctamente");
		} catch (IOException x) {
			logger.error("Error presentado en el metodo readFileLocal" + x.getMessage());
		}
		return listWebSiteDefault;
	}
    /*
     * (non-Javadoc)
     * @see com.belatrix.parsemainpage.app.processor.IFileListWebSiteProcessor#reportHashTagFile(java.util.Map)
     */
	@Override
	public boolean reportHashTagFile(Map<String, List<String>> mapHashTag) {
		logger.info("Inicio del metodo - reportHashTagFile");
		String fileSeparator = System.getProperty("file.separator");
		logger.debug("El separador del sistema es: " +fileSeparator);

		if (mapHashTag != null) {
			mapHashTag.entrySet().forEach(x -> {
				String absoluteFilePath = PATH_BASE.concat(fileSeparator).concat(x.getKey()).concat(".txt");
				logger.debug("Ruta absoluta del archivo: " +absoluteFilePath);

				File file = new File(absoluteFilePath);
				try (FileOutputStream fop = new FileOutputStream(file)) {
					if (!file.exists()) {
						logger.info("El archivo no existe se procede a crear");
						file.createNewFile();
					}
					logger.debug("Escribiendo linea..." );
					x.getValue().forEach(i -> {
						try {
							logger.debug("Linea: " +i );
							fop.write(i.getBytes());
							fop.write("\r\n".getBytes());
						} catch (IOException e) {
							logger.error("Se presento error escribiendo linea " + i);
							logger.error("Causa: " + e.getMessage());
						}
					});
					fop.flush();
				} catch (IOException e) {
					logger.error("Se presento error creando el archivo ");
					logger.error("Causa: " + e.getMessage());
				}
			});
			logger.info("Se procesa el reporte correctamente");
			return true;
		}
		return false;
	}

}

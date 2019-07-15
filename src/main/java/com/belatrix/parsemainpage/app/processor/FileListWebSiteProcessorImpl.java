package com.belatrix.parsemainpage.app.processor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * Log handling for processor file
 * @author SAID HERNANDEZ
 *
 */
@Component
public class FileListWebSiteProcessorImpl implements IFileListWebSiteProcessor {
  

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
	public boolean reportHashTagFile(Map<String, List<String>> mapHashTag, HttpServletResponse response) {
		logger.info("Inicio del metodo - reportHashTagFile");
		ArrayList<File> files = new ArrayList<>();
		if (mapHashTag != null) {
			try {
				response.setStatus(HttpServletResponse.SC_OK);
				response.addHeader("Content-Disposition", "attachment; filename=\"ResultReportHash.zip\"");
				ZipOutputStream zipOutputStream = new ZipOutputStream(response.getOutputStream());
				for (Map.Entry<String, List<String>> entry : mapHashTag.entrySet()) {
					String absoluteFilePath = entry.getKey().concat(".txt");
					logger.debug("Ruta absoluta del archivo: " + absoluteFilePath);
					File file = new File(absoluteFilePath);
					FileOutputStream fop = new FileOutputStream(file);
					logger.debug("Escribiendo linea...");
					entry.getValue().forEach(i -> {
						try {
							fop.write(i.getBytes());
							fop.write("\r\n".getBytes());
						} catch (IOException e) {
							logger.error("Se presento error escribiendo linea " + i);
							logger.error("Causa: " + e.getMessage());
						}
					});
					fop.flush();
					fop.close();
					files.add(file);
				}
				for (File file : files) {
			        zipOutputStream.putNextEntry(new ZipEntry(file.getName()));
			        FileInputStream fileInputStream = new FileInputStream(file);
			        IOUtils.copy(fileInputStream, zipOutputStream);
			        fileInputStream.close();
			        zipOutputStream.closeEntry();
			    }    
			    zipOutputStream.close();
			} catch (IOException e1) {
				logger.error("Se presento error construyendo el zip Causa: " + e1.getMessage());
			}
		}
		return false;
	}

}

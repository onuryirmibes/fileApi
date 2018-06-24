package de.exb.platform.cloud.fileapi.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import de.exb.platform.cloud.fileapi.exception.CallServiceException;
import de.exb.platform.cloud.fileapi.model.FileMetadata;
import de.exb.platform.cloud.fileapi.property.FileServerProperties;
import de.exb.platform.cloud.fileapi.security.AuthController;

@Component
public class FileServerService {

	private static final Logger LOGGER = LogManager.getLogger(FileServerService.class);

	@Autowired
	private FileServerProperties properties;
	
	@Autowired
	private AuthController authController;

	private HttpHeaders setHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", authController.getToken());
		headers.setExpires(0);
		headers.setCacheControl("private, no-store, max-age=0");
		return headers;
	}

	public FileMetadata upload(MultipartFile file) {

		HttpEntity<MultipartFile> request = new HttpEntity<>(file, setHeader());
		RestTemplate restTemplate = new RestTemplate();
		String url = properties.getFileUri() + "/uploadFile";
		ResponseEntity<FileMetadata> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, request, FileMetadata.class);
		} catch (Exception ex) {
			logAndThrowCallServiceException(url, ex);
		}

		if (response == null) {
			throw new CallServiceException("File Service returned empty");
		}

		return response.getBody();
	}
	
	public List<FileMetadata> upload(MultipartFile[] files) {

		HttpEntity<MultipartFile[]> request = new HttpEntity<>(files, setHeader());
		RestTemplate restTemplate = new RestTemplate();
		String url = properties.getFileUri() + "/uploadMultipleFiles";
		ResponseEntity<FileMetadata[]> response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.POST, request, FileMetadata[].class);
		} catch (Exception ex) {
			logAndThrowCallServiceException(url, ex);
		}

		if (response == null) {
			throw new CallServiceException("File Service returned empty");
		}

		FileMetadata[] resultArray = response.getBody();
		return Arrays.asList(resultArray);
	}

	public Resource download(UUID fileId) {

		HttpEntity<String> request = new HttpEntity<>(setHeader());
		RestTemplate restTemplate = new RestTemplate();
		String url = properties.getFileUri() + "/downloadFile/" + fileId.toString();
		ResponseEntity<Resource> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET, request, Resource.class);
		} catch (Exception ex) {
			logAndThrowCallServiceException(url, ex);
		}
		if (response == null) {
			throw new CallServiceException("File Service returned empty");
		}
		return response.getBody();
	}

	public void delete(UUID fileId) {

		HttpEntity<String> request = new HttpEntity<>(setHeader());
		RestTemplate restTemplate = new RestTemplate();
		String url = properties.getFileUri() + "/downloadFile/" + fileId.toString();
		ResponseEntity<Void> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.DELETE, request, Void.class);
		} catch (Exception ex) {
			logAndThrowCallServiceException(url, ex);
		}
		if (response == null) {
			throw new CallServiceException("File Service returned empty");
		}
		if (response.getStatusCode() != HttpStatus.OK) {
			throw new CallServiceException("File could not be deleted");
		}
	}

	private void logAndThrowCallServiceException(String url, Exception ex) {
		LOGGER.info("Unsuccessful service call. URL: " + url, ex);
		throw new CallServiceException("Can not get User Info from service");
	}

}

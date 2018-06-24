package de.exb.platform.cloud.fileapi.service;

import java.util.List;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.exb.platform.cloud.fileapi.controller.FileMetadataController;
import de.exb.platform.cloud.fileapi.model.FileMetadata;

@CrossOrigin
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1/metadata")
public class FileMetadataApiService {
	
	private static final Logger LOGGER = LogManager.getLogger(FileApiService.class);
	
	@Autowired
	private FileMetadataController controller;
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FileMetadata> readAll() {
		List<FileMetadata> returnPayload = controller.findAll();
		LOGGER.info("Outgoing Payload Value For Get All: " + writeValueAsString(returnPayload));
		return returnPayload;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public FileMetadata read(@PathVariable("id") UUID id) {
		LOGGER.info("Incoming Payload Value For Get With Id: " + id.toString());
		FileMetadata returnPayload = controller.findOne(id);
		LOGGER.info("Outgoing Payload Value For Get With Id: " + writeValueAsString(returnPayload));
		return returnPayload;
	}
	
	@RequestMapping(value = "/extensions/{ext}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<FileMetadata> read(@PathVariable("ext") String ext) {
		LOGGER.info("Incoming Payload Value For Get With Extension: " + ext);
		List<FileMetadata> returnPayload = controller.findByExtension(ext);
		LOGGER.info("Outgoing Payload Value For Get With Extension: " + writeValueAsString(returnPayload));
		return returnPayload;
	}
	
	@RequestMapping(value = "/filter", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<FileMetadata> filter(@RequestBody FileMetadata fileMetadata) {
		LOGGER.info("FileMetadata filter service initiated");
		List<FileMetadata> returnPayload = controller.filter(fileMetadata);
		LOGGER.info("FileMetadata filter service completed");
		return returnPayload;
	}
	
	public String writeValueAsString(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException jsonException) {
			LOGGER.info("Failed to convert Json To String : " + jsonException);
		}
		return "";
	}
}

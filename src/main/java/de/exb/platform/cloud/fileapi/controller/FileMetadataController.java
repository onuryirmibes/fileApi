package de.exb.platform.cloud.fileapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.exb.platform.cloud.fileapi.model.FileMetadata;

@Component
public class FileMetadataController {
	
	@Autowired
	private FileMetadataServerService service;

	public List<FileMetadata> findAll() {
		return service.getFileMetadataList();
	}

	public FileMetadata findOne(UUID fileId) {
		return service.getFileMetadataById(fileId);
	}

	public List<FileMetadata> filter(FileMetadata filter) {
		return service.getFileMetadataListByFilter(filter);
	}

	public List<FileMetadata> findByExtension(String ext) {
		FileMetadata filter = new FileMetadata();
		filter.setExtension(ext);
		return service.getFileMetadataListByFilter(filter);
	}

}

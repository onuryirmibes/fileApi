package de.exb.platform.cloud.fileapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import de.exb.platform.cloud.fileapi.exception.NoDataFound;
import de.exb.platform.cloud.fileapi.exception.NotSpecifiedRequest;
import de.exb.platform.cloud.fileapi.model.FileMetadata;

@Component
public class FileController {
	
	@Autowired
	private FileServerService fileServerService;
	
	@Autowired 
	private FileMetadataServerService fileMetadataServerService;

	public FileMetadata uploadFile(MultipartFile file) {
		return fileServerService.upload(file);
	}

	public List<FileMetadata> uploadMultipleFiles(MultipartFile[] files) {
		return fileServerService.upload(files);
	}

	public Resource downloadFileById(UUID fileId) {
		return fileServerService.download(fileId);
	}

	public void deleteFile(UUID fileId) {
		fileServerService.delete(fileId);
	}

	public Resource downloadFileByName(String fileName) {
		FileMetadata filter = new FileMetadata();
		filter.setName(fileName);
		List<FileMetadata> fileMetadataResults = getFileMetadataByFilter(filter);
		if(fileMetadataResults.size() == 1){
			UUID fileId = fileMetadataResults.get(0).getId();
			return downloadFileById(fileId);
		} else if(fileMetadataResults.isEmpty()) {
			throw new NoDataFound("No file found with given name : " + fileName);
		} else {
			int count = fileMetadataResults.size();
			throw new NotSpecifiedRequest(
					"There are " + count + " different file with given name : " + fileName);
		}
	}

	public List<FileMetadata> getFileMetadataByFilter(FileMetadata filter) {
		return fileMetadataServerService.getFileMetadataListByFilter(filter);
	}

}

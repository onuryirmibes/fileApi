package de.exb.platform.cloud.fileapi.model;

import java.nio.file.Path;
import java.util.UUID;

public class FileMetadata {
	
	private UUID id;

	private Path path;
	
	private String name;
	
	private String extension;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Path getPath() {
		return path;
	}

	public void setPath(Path path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
	
}

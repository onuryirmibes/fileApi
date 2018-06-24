package de.exb.platform.cloud.fileapi.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fileserver")
public class FileServerProperties {
	
	private String fileUri;
	private String fileMetadataUri;

	public String getFileUri() {
		return fileUri;
	}

	public void setFileUri(String fileUri) {
		this.fileUri = fileUri;
	}

	public String getFileMetadataUri() {
		return fileMetadataUri;
	}

	public void setFileMetadataUri(String fileMetadataUri) {
		this.fileMetadataUri = fileMetadataUri;
	}

}

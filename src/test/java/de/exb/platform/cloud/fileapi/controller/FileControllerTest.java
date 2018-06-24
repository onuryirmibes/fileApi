package de.exb.platform.cloud.fileapi.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import de.exb.platform.cloud.fileapi.model.FileMetadata;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FileController.class, secure = false)
@ContextConfiguration(classes = { FileController.class, FileMetadata.class })
public class FileControllerTest {

	private FileController spyController;

	@Autowired
	MockMvc mockMVC;

	@Autowired
	private ResourceLoader resourceLoader;

	final Resource fileResource = resourceLoader.getResource("classpath:src/test/resources/testFile.jpg");

	@Test
	public void downloadFileByName_succesful_test() {
		spyController = Mockito.spy(FileController.class);
		
		FileMetadata fileMetadata = new FileMetadata();
		List<FileMetadata> fileMetadataList = new ArrayList<>();
		fileMetadataList.add(fileMetadata);
		Mockito.doReturn(fileMetadataList).when(spyController).getFileMetadataByFilter(Mockito.any(FileMetadata.class));
		Mockito.doReturn(fileResource).when(spyController).downloadFileById(Mockito.any(UUID.class));
		
		Resource result = spyController.downloadFileByName("testFile");
		assertEquals(fileResource.getFilename(), result.getFilename());
	}
}

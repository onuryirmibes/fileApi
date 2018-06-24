package de.exb.platform.cloud.fileapi.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import de.exb.platform.cloud.fileapi.model.FileMetadata;

@RunWith(SpringRunner.class)
@WebMvcTest(value = FileApiService.class, secure = false)
@ContextConfiguration(classes = { FileApiService.class, FileMetadata.class })
public class FileApiServiceTest {
	
	@MockBean
	private FileApiService fileApiService;
	
	@Autowired
	MockMvc mockMVC;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	final Resource fileResource = resourceLoader.getResource("classpath:src/test/resources/testFile.jpg");
	
	@Test
	public void downloadFile_ServiceTest() {
		Mockito.when(fileApiService.downloadFile(Mockito.any(String.class))).thenReturn(fileResource);
		RequestBuilder rb = MockMvcRequestBuilders.get("/api/v1/files/downloadFile/testFile");
		MvcResult response = performRB(rb);
		int status = response.getResponse().getStatus();
		assertEquals(HttpStatus.OK, status);
	}
	
	public MvcResult performRB(RequestBuilder rb) {
		try {
			return mockMVC.perform(rb).andReturn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

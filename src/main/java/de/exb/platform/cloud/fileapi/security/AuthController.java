package de.exb.platform.cloud.fileapi.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class AuthController {
	
	public String getBearerToken(){
		return getRequest().getHeader("Authorization");
	}

	public String getToken() {
		String bearerToken = getBearerToken();
		return clearToken(bearerToken);
	}

	private HttpServletRequest getRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	}

	private String clearToken(String bearerToken) {
		return bearerToken.replaceAll("bearer ", "").replaceAll("Bearer ", "");
	}
}

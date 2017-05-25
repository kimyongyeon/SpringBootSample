package com.tistory.heowc.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tistory.heowc.auth.jwt.JwtFactory;
import com.tistory.heowc.auth.jwt.JwtInfo;
import com.tistory.heowc.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class BaseSecurityHandler implements AuthenticationSuccessHandler, AuthenticationFailureHandler {

	private final JwtFactory JwtFactory;

	private final ObjectMapper objectMapper;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request,
										HttpServletResponse response,
										Authentication authentication) throws IOException, ServletException {
		Member member = (Member)authentication.getPrincipal();
		String jsonToMember = objectMapper.writeValueAsString(member);
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		response.setHeader(JwtInfo.HEADER_NAME, JwtFactory.createToken(jsonToMember));
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,
										HttpServletResponse response,
										AuthenticationException exception) throws IOException, ServletException {
		throw exception;
	}
}

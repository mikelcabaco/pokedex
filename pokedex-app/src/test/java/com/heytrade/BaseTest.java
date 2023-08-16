package com.heytrade;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heytrade.pokedex.config.constants.SpringConfigConstants;

import jakarta.annotation.PostConstruct;

/**
 * Base test for all application tests 
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@AutoConfigureMockMvc
@SpringBootTest
@Testcontainers
public abstract class BaseTest {
	
	@Container
	private static final DatabaseContainer sqlContainer = DatabaseContainer.getInstance();

	/** The mockMvc to call endpoints */
	@Autowired
	protected MockMvc mockMvc;

	/** Injected web application context */
	@Autowired
	protected WebApplicationContext context;
	
	protected ObjectMapper mapper = new ObjectMapper();
	
	
	
	/** Check if Database is running before execute any test. */
	@PostConstruct
	private void initMocks() {
		Assertions.assertTrue(sqlContainer.isRunning());
	}
	
	/**
	 * Fake login for specified <code>username</code>
	 * 
	 * @throws Exception en caso de error
	 */
	protected void login(MockHttpServletRequestBuilder requestBuilder, String username) throws Exception {
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_" + SpringConfigConstants.Security.USER_ROLE));
		var auth = new UsernamePasswordAuthenticationToken(username, null, authorities);
		SecurityContextHolder.getContext().setAuthentication(auth);
	}
}

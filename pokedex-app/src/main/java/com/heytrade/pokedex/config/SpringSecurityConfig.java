package com.heytrade.pokedex.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AnonymousConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;

import com.heytrade.pokedex.common.constants.CommonConstants;
import com.heytrade.pokedex.config.constants.SpringConfigConstants;


/**
 * Configuration class for Spring Security
 *
 * @author HEYTRADE
 * @since 1.0.0
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SpringSecurityConfig {

	/**
	 * Http security config.
	 * @param http {@link HttpSecurity} the http security configurer
	 * @return {@link SecurityFilterChain} the configured filter chain
	 * @throws Exception if error
	 */
	@Bean
	protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(c ->
			c.requestMatchers(CommonConstants.API_BASE + SpringConfigConstants.PATTERN_ALL).authenticated()
		)
		.authorizeHttpRequests(authorizedRequest -> authorizedRequest
				.requestMatchers(
					"/swagger-ui/**",
					"/v3/api-docs/**"
				).permitAll()
				// All other pages are denied by default if not role is assigned
				.anyRequest().denyAll()
			);

		//Default header for all responses
		http.headers(headers ->
			headers
				.contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; frame-ancestors 'self'; object-src 'none'; script-src 'self' 'unsafe-inline'; img-src 'self' data:"))
				.referrerPolicy(referrer -> referrer.policy(ReferrerPolicy.STRICT_ORIGIN))
				.frameOptions(frame -> frame.sameOrigin())
		)
		
		// Disable CSRF
		.csrf(csrf -> csrf.disable())
		
		// Allow a basic configuration
		.httpBasic(Customizer.withDefaults());
		
		http.anonymous(AnonymousConfigurer::disable);
		
		// Add in memory authentication
		http.userDetailsService(configAuthentication());
		
		return http.build();
	}
	
	/**
	 * Setup application password encoder.
	 * @return passwordEncoder {@link PasswordEncoder}
	 */
	@Bean
	PasswordEncoder passwordEncoder() {
		return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
	}
	
	
	/**
	 * Setup authentication provider in memory.
	 * @return {@link InMemoryUserDetailsManager}
	 */
	@Bean
	protected InMemoryUserDetailsManager configAuthentication() {
		List<UserDetails> users = new ArrayList<>();

		String user = System.getenv(SpringConfigConstants.Security.USERNAME_ENV);
		String pwd = System.getenv(SpringConfigConstants.Security.PASSWORD_ENV);
		if (null != StringUtils.trimToNull(user) && null != StringUtils.trimToNull(pwd)) {
			List<GrantedAuthority> authority = new ArrayList<>();
			authority.add(new SimpleGrantedAuthority("ROLE_" + SpringConfigConstants.Security.USER_ROLE));
			UserDetails admin = new User(user, passwordEncoder().encode(pwd), authority);
			users.add(admin);
		}
		return new InMemoryUserDetailsManager(users);
	}
	
}

package com.heytrade.pokedex.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;

import com.heytrade.pokedex.audit.aop.AuditableAdvisor;
import com.heytrade.pokedex.audit.aop.AuditableInterceptor;

import lombok.extern.slf4j.Slf4j;


/**
 * Configuration class for spring context
 *
 * @author HEYTRADE
 * @since 1.0.0
 */
@Slf4j
@Configuration
@ComponentScan(
		basePackages = "com.heytrade.pokedex", 
		excludeFilters = {
				//Excludes all controllers
				@Filter(value = RestController.class),
				@Filter(value = ControllerAdvice.class),
				//Excludes own configuration classes
				@Filter(pattern = "com.heytrade.pokedex.config", type = FilterType.REGEX)}
	)
public class SpringContextConfig {
	
	
	@Bean
	@ConditionalOnProperty(prefix="config", name={"audit.enabled"}, havingValue="true", matchIfMissing = true)
	AuditableAdvisor auditableAdvisor() {
		log.debug("Creating auditable advisor");
		return new AuditableAdvisor(auditableInterceptor());
	}
	
	@Bean
	@ConditionalOnProperty(prefix="config", name={"audit.enabled"}, havingValue="true", matchIfMissing = true)
	AuditableInterceptor auditableInterceptor() {
		log.debug("Creating auditable interceptor");
		return new AuditableInterceptor();
	}
	
}

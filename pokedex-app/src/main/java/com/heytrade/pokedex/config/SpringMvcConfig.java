package com.heytrade.pokedex.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration class for Spring MVC
 *
 * @author HEYTRADE
 * @since 1.0.0
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.heytrade.pokedex"}, includeFilters = @Filter(value = RestController.class))
public class SpringMvcConfig implements WebMvcConfigurer {

}

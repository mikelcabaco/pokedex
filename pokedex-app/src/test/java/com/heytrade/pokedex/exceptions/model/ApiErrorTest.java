package com.heytrade.pokedex.exceptions.model;

import java.util.ArrayList;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.heytrade.pokedex.audit.model.Audit;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * Test class for {@link Audit}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
class ApiErrorTest {
	
	/**
	 * Test method with library equalsVerifier.
	 */
	@Test
	void testApiErrorTest() {
		EqualsVerifier.forClass(ApiError.class)
		.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
		.withPrefabValues(Map.class, Map.of("OK", new ArrayList<>()), Map.of("KO", new ArrayList<>()))
		.withRedefinedSuperclass()
		.verify();
	}
	
	
	/**
	 * Test method with multiple errors. 
	 */
	@Test
	void testWhenStringErrorIsPresentThenListOfPairErrorsIsCreated() {
		ApiError apiError = new ApiError(HttpStatus.OK, "Test message", "Error 1,", "Error 2", "Error n");
		Assertions.assertNotNull(apiError.getErrors());
		Assertions.assertEquals(3, apiError.getErrors().size());
	}

}

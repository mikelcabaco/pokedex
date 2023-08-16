package com.heytrade.pokedex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.heytrade.BaseTest;

/**
 * Test class for {@link WebApplicationContext}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
class HeytradeApplicationTestIT extends BaseTest {

	/**
	 * Test method getApplicationContext.
	 * Expect to be not null.
	 */
	@Test
	void testGetApplicationContext() throws Throwable {
		Assertions.assertNotNull(context);
		
	}
}

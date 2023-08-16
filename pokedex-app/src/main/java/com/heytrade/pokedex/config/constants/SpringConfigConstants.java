package com.heytrade.pokedex.config.constants;


/**
 * Spring constants for application configuration
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public final class SpringConfigConstants {

	public static final String PATTERN_ALL = "/**";
	
	

	/**
	 * Default constructor<br>
	 */
	private SpringConfigConstants() { }

	
	public static final class Security {
		public static final String USER_ROLE = "USER";
		public static final String USERNAME_ENV = "USER";
		public static final String PASSWORD_ENV = "PASSWORD";
		
		private Security() { }
	}
}
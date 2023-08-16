package com.heytrade.pokedex.commons.exceptions;


/**
 * Core checked exception to extend in application
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public abstract class CoreException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 14443223432L;
	
	/** Custom error message to show */
	private final String errorMessage;

	/**
	 * Instantiates a new core exception.
	 *
	 * @param message the message
	 */
	protected CoreException(String message) {
		super(message);
		this.errorMessage = message;
	}
	
	
	/**
	 * Returns the error message of the exception
	 * 
	 * @return String with error message or null if not defined
	 */
	public String getErrorMessage() {
		return this.errorMessage;
	}
}

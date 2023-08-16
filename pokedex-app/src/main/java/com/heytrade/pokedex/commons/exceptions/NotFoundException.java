package com.heytrade.pokedex.commons.exceptions;


/**
 * Web exception for rest operation that not found the requested resource
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public class NotFoundException extends CoreException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 14443223430L;

	/**
	 * Instantiates a not found exception.
	 *
	 * @param message the message
	 */
	public NotFoundException(String message) {
		super(message);
	}
}

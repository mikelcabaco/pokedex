package com.heytrade.pokedex.commons.exceptions;

import org.springframework.validation.Errors;

import com.heytrade.pokedex.i18n.constants.I18nConstants;

import lombok.Getter;


/**
 * Web exception for rest operation that has bad request or invalid input data
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Getter
public class BadRequestException extends CoreException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 14443223429L;
	
	/** 
	 * The errors detected
	 * @return {@link Errors} the errors
	 */
	private final transient Errors errors;
	
	
	/**
	 * Instantiates a bad request exception with specified errors and default message.
	 *
	 * @param errors produced in request
	 */
	public BadRequestException(Errors errors) {
		super(I18nConstants.I18N_VALIDATION_INVALID_DATA);
		this.errors = errors;
	}

}

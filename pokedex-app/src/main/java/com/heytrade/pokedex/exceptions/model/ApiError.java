package com.heytrade.pokedex.exceptions.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Response object for request that has any type of error
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ApiError {
 
	/** 
	 * Response status of api error.
	 * @param status {@link HttpStatus} The status
	 * @return {@link HttpStatus} the status
	 */
	private HttpStatus status;
	
	/** 
	 * Response message of api error.
	 * @param message {@link String} The message
	 * @return {@link String} the message
	 */
	private String message;
	
	/** 
	 * List of errors.
	 * @param errors {@link List} The errors
	 * @return {@link List} the errors
	 */
	private List<Map<String, List<Object>>> errors;
	
	
	/** 
	 * Create a new ApiError with specified status, message and errors
	 * 
	 * @param status HttpStatus to response
	 * @param message The message to show
	 * @param errors The errors to show
	 */
	public ApiError(HttpStatus status, String message, String... errors) {
		super();
		this.status = status;
		this.message = message;
		
		this.errors = new ArrayList<>();
		if (errors != null) {
			Map<String, List<Object>> pairError;
			for (String error : errors) {
				pairError = Map.of(error, new ArrayList<Object>());
				this.errors.add(pairError);
			}
		}
	}
}
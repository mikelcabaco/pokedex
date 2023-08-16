package com.heytrade.pokedex.crud.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.heytrade.pokedex.commons.exceptions.BadRequestException;
import com.heytrade.pokedex.commons.exceptions.CoreException;
import com.heytrade.pokedex.commons.model.Core;
import com.heytrade.pokedex.commons.model.DataLoad;
import com.heytrade.pokedex.commons.model.Page;
import com.heytrade.pokedex.crud.constants.CrudConstants;
import com.heytrade.pokedex.crud.service.CrudService;
import com.heytrade.pokedex.exceptions.model.ApiError;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


/**
 * Generic controller interface that implements generic method for CRUD operations.
 * 
 * @param <T> Any object extending {@link Core}
 * @param <P> A object extending Page of T
 * @author HEYTRADE
 * @since 1.0.0
 */
public interface CrudController<T extends Core<?>, P extends Page<T>> {
	
	/**
	 * Get the service class of this controller
	 * 
	 * @return {@link CrudService} inside the controller
	 */
	CrudService<T> getService();
	
	/**
	 * Get the entity validator for this controller<br>
	 * By default it will be used to validate incoming data from insert/update/delete methods.
	 * If you need a specific validator for each method (insert/update/delete) you must override
	 * {@link CrudController#getInsertValidator()}, {@link CrudController#getUpdateValidator()}
	 *  or {@link CrudController#getDeleteValidator()} respectively
	 * @return {@link Validator} that supports T record type
	 */
	Validator getValidator();
	
	/**
	 * Get the validator used to validate the insert method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getInsertValidator() {
		return getValidator();
	}
	
	/**
	 * Get the validator used to validate the update method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getUpdateValidator() {
		return getValidator();
	}
	
	/**
	 * Get the validator used to validate the delete method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getDeleteValidator() {
		return getValidator();
	}
	
	/**
	 * Get the validator used to validate the find method<br>
	 * Default implementation returns {@link CrudController#getValidator()}
	 * @return {@link Validator} that supports T record type
	 */
	default Validator getFindValidator() {
		return null;
	}

	/**
	 * Get the search validator for this controller<br>
	 * It will be user to validate incoming data from search method
	 * @return {@link Validator} that supports {@link Page}&lt;T&gt; record type
	 */
	Validator getSearchValidator();
	
	/**
	 * Persist a new record of T 
	 * 
	 * @param dto the record to persist
	 * @param bindingResult Binding result for error validation
	 * @return T persisted record
	 * @throws CoreException in case of error
	 */
	@Operation(
		summary = "Add a new record to application.",
		responses = {
			@ApiResponse(responseCode = "201", description = "Record has been created successfully"),
			@ApiResponse(responseCode = "400", description = "Request parameters are invalid.",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				}),
			@ApiResponse(responseCode = "500", description = "Internal server error",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				})
		}
	)
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_INSERT, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<T> insert(@RequestBody T dto, BindingResult bindingResult) throws CoreException {
		this.validate(this.getInsertValidator(), dto, bindingResult, null);
		this.getService().insert(dto);
		return new ResponseEntity<>(dto, HttpStatus.CREATED);
	}
	
	/**
	 * Updates an existing record 
	 * 
	 * @param dto the record to update
	 * @param bindingResult Binding result for error validation
	 * @return Void
	 * @throws CoreException in case of error
	 */
	@Operation(
		summary = "Update an existing record in application.",
		responses = {
			@ApiResponse(responseCode = "202", description = "Record has been updated successfully"),
			@ApiResponse(responseCode = "400", description = "Request parameters are invalid.",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				}),
			@ApiResponse(responseCode = "500", description = "Internal server error",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				})
		}
	)
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_UPDATE, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<Void> update(@RequestBody T dto, BindingResult bindingResult, Model model) throws CoreException {
		this.validate(this.getUpdateValidator(), dto, bindingResult, null);
		this.getService().update(dto);
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}
	
	/**
	 * Delete an existing record
	 * 
	 * @param dto the record to delete
	 * @param bindingResult Binding result for error validation
	 * @return Void
	 * @throws CoreException in case of error
	 */
	@Operation(
		summary = "Delete an existing record in application.",
		responses = {
			@ApiResponse(responseCode = "204", description = "Record has been deleted successfully"),
			@ApiResponse(responseCode = "400", description = "Request parameters are invalid.",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				}),
			@ApiResponse(responseCode = "500", description = "Internal server error",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				})
		}
	)
	@PostMapping(value = CrudConstants.CRUD_CONTROLLER_DELETE, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<Void> delete(@RequestBody T dto, BindingResult bindingResult) throws CoreException {
		this.validate(this.getDeleteValidator(), dto, bindingResult, null);
		this.getService().delete(dto);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	/**
	 * Find a record of T
	 * 
	 * @param dto the record to find
	 * @param bindingResult Binding result for error validation
	 * @return T record data
	 * @throws CoreException in case of error
	 */
	@Operation(
		summary = "Find an existing record in application.",
		responses = {
			@ApiResponse(responseCode = "200", description = "Record has been found successfully"),
			@ApiResponse(responseCode = "400", description = "Request parameters are invalid.",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				}),
			@ApiResponse(responseCode = "404", description = "Record has not been found.",
			content = {
				@Content(
					mediaType = MediaType.APPLICATION_JSON_VALUE,
					array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
			}),
			@ApiResponse(responseCode = "500", description = "Internal server error",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				})
		}
	)
	@GetMapping(value = CrudConstants.CRUD_CONTROLLER_FIND, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<T> find(@RequestBody T dto, BindingResult bindingResult) throws CoreException {
		this.validate(this.getFindValidator(), dto, bindingResult, null);
		var result = this.getService().find(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Find all records of T
	 * 
	 * @param dto with search criteria
	 * @param bindingResult Binding result for error validation
	 * @return {@link Page} of T that meets the search criteria
	 * @throws CoreException in case of error
	 */
	@Operation(
		summary = "Search records in application.",
		responses = {
			@ApiResponse(responseCode = "200", description = "Records has been searched successfully"),
			@ApiResponse(responseCode = "400", description = "Request parameters are invalid.",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				}),
			@ApiResponse(responseCode = "500", description = "Internal server error",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				})
		}
	)
	@GetMapping(value = CrudConstants.CRUD_CONTROLLER_SEARCH, 
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<Page<T>> search(@RequestBody P dto, BindingResult bindingResult) throws CoreException {
		this.validate(this.getSearchValidator(), dto, bindingResult, this.getService().initPage());
		Page<T> result = this.getService().search(dto);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	/**
	 * Prepare the data used when access the search page.
	 * 
	 * @throws CoreException in case of error
	 */
	@Operation(
		summary = "Prepare the data used when access the search page.",
		responses = {
			@ApiResponse(responseCode = "200", description = "Records has been searched successfully"),
			@ApiResponse(responseCode = "400", description = "Request parameters are invalid.",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				}),
			@ApiResponse(responseCode = "500", description = "Internal server error",
				content = {
					@Content(
						mediaType = MediaType.APPLICATION_JSON_VALUE,
						array = @ArraySchema(schema = @Schema(implementation = ApiError.class)))
				})
		}
	)
	@GetMapping(value = CrudConstants.CRUD_CONTROLLER_INIT_PAGE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public default ResponseEntity<DataLoad> initPage() throws CoreException {
		DataLoad result = this.getService().initPage();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	
	
	/**
	 * Check for input validation
	 * 
	 * @param validator to use for validation
	 * @param dto Object to validate
	 * @param bindingResult to store validation errors
	 * @param validationData {@link Object} the validation data to pass within model and errors to validator
	 * @throws BadRequestException if validation not success
	 */
	default void validate(Validator validator, Object dto, BindingResult bindingResult, DataLoad validationData) throws CoreException {
		if (null != validator) {
			if (validator instanceof CrudValidator crudValidator) {
				crudValidator.setValidationData(validationData);
			}
			ValidationUtils.invokeValidator(validator, dto, bindingResult);
			if (bindingResult.hasErrors()) {
				throw new BadRequestException(bindingResult);
			}
		}
	}
}
package com.heytrade.pokedex.crud.web;

import org.springframework.validation.Validator;

import com.heytrade.pokedex.commons.model.DataLoad;

import lombok.Data;


/**
 * Generic CRUD validator implementing {@link Validator} for CRUD controllers
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
public abstract class CrudValidator implements Validator {
	
	/** 
	 * The validation data used to validate user inputs
	 * 
	 * @param validationData The validation data
	 * @return The validation data
	 * */
	private DataLoad validationData;
}
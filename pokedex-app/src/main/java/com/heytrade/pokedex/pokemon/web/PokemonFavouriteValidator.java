package com.heytrade.pokedex.pokemon.web;

import org.springframework.validation.Errors;

import com.heytrade.pokedex.common.constants.CommonConstants;
import com.heytrade.pokedex.common.utils.ValidatorUtils;
import com.heytrade.pokedex.crud.web.CrudValidator;
import com.heytrade.pokedex.pokemon.model.Pokemon;


/**
 * Favourite Validator for {@link Pokemon}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public class PokemonFavouriteValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return Pokemon.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// Mandatory fields
		ValidatorUtils.rejectIfEmptyOrWhitespace(errors, CommonConstants.FIELD_ID);
	}
}

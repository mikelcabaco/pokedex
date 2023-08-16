package com.heytrade.pokedex.pokemon.web;

import org.springframework.validation.Errors;

import com.heytrade.pokedex.common.constants.CommonConstants;
import com.heytrade.pokedex.common.utils.ValidatorUtils;
import com.heytrade.pokedex.crud.web.CrudValidator;
import com.heytrade.pokedex.pokemon.constants.PokemonConstants;
import com.heytrade.pokedex.pokemon.model.PokemonDataLoad;
import com.heytrade.pokedex.pokemon.model.PokemonPage;

/**
 * Search Validator for {@link PokemonPage}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public class PokemonPageValidator extends CrudValidator {

	@Override
	public boolean supports(Class<?> clazz) {
		return PokemonPage.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		var initialData = (PokemonDataLoad) this.getValidationData();
		
		// Max length validations
		ValidatorUtils.rejectIfLengthExceeded(errors, PokemonConstants.FIELD_FILTERS_NAME, 50);
		
		
		// Validate pokemon types
		ValidatorUtils.rejectIfCollectionNotContains(errors,
				PokemonConstants.FIELD_FILTERS_TYPES, CommonConstants.FIELD_ID, initialData.getTypes());
	}

}

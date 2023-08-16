package com.heytrade.pokedex.pokemon.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.heytrade.pokedex.commons.exceptions.CoreException;
import com.heytrade.pokedex.crud.service.CrudService;
import com.heytrade.pokedex.crud.web.CrudController;
import com.heytrade.pokedex.exceptions.model.ApiError;
import com.heytrade.pokedex.pokemon.constants.PokemonConstants;
import com.heytrade.pokedex.pokemon.model.Pokemon;
import com.heytrade.pokedex.pokemon.model.PokemonPage;
import com.heytrade.pokedex.pokemon.service.PokemonService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


/**
 * Controller for operations with Pokemon module
 *
 * @author HEYTRADE
 * @since 1.0.0
 */
@RestController
@RequestMapping(PokemonConstants.CONTROLLER_NAMESPACE)
public class PokemonController implements CrudController<Pokemon, PokemonPage>{

	/** The associated service */
	@Autowired
	private PokemonService pokemonService;
	
	
	@Override
	public CrudService<Pokemon> getService() {
		return pokemonService;
	}

	@Override
	public Validator getValidator() {
		return null;
	}
	
	@Override
	public Validator getFindValidator() {
		return new PokemonFindValidator();
	}

	@Override
	public Validator getSearchValidator() {
		return new PokemonPageValidator();
	}
	
	public Validator getFavouriteValidator() {
		return new PokemonFavouriteValidator();
	}
	
	
	/**
	 * Add and remove a Pokemon to/from your Favourites 
	 * 
	 * @param dto the pokemon.
	 * @param bindingResult Binding result for error validation
	 * @return Pokemon updated pokemon
	 * @throws CoreException in case of error
	 */
	@Operation(
		summary = "Add and remove a Pokemon to/from your Favourites.",
		responses = {
			@ApiResponse(responseCode = "202", description = "Pokemon has been updated successfully"),
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
	@PostMapping(value = PokemonConstants.CONTROLLER_FAVOURITES_POKEMONS,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Pokemon> setOrUnsetFavourite(@RequestBody Pokemon dto, BindingResult bindingResult) throws CoreException {
		this.validate(this.getFavouriteValidator(), dto, bindingResult, null);
		var result = pokemonService.setOrUnsetFavourite(dto);
		return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
	}
}

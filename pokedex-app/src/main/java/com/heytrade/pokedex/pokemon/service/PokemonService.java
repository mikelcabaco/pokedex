package com.heytrade.pokedex.pokemon.service;

import com.heytrade.pokedex.commons.exceptions.NotFoundException;
import com.heytrade.pokedex.crud.service.CrudService;
import com.heytrade.pokedex.pokemon.model.Pokemon;


/**
 * Pokemon service interface to handle pokemons
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public interface PokemonService extends CrudService<Pokemon> {

	/**
	 * Add and remove a Pokemon to/from your Favourites.
	 * @return {@link Pokemon} the pokemon 
	 */
	Pokemon setOrUnsetFavourite(Pokemon p) throws NotFoundException;
}

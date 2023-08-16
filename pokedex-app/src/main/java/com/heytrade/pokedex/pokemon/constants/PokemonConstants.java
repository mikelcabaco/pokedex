package com.heytrade.pokedex.pokemon.constants;

import com.heytrade.pokedex.common.constants.CommonConstants;


/**
 * Constants for pokemon module
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public final class PokemonConstants {

	/** Controller request for all requests */
	public static final String CONTROLLER_NAMESPACE = CommonConstants.API_BASE + "/pokemon";
		
	/** CONTROLLER_GET_FAVOURITES_POKEMONS */
	public static final String CONTROLLER_FAVOURITES_POKEMONS = "/favourite";
	
	
	/** Pokemon attribute name */
	public static final String FIELD_NAME = "name";
	
	/** Pokemon attribute types */
	public static final String FIELD_TYPES = "types";
	
	/** Page Pokemon attribute filters name */
	public static final String FIELD_FILTERS_NAME = CommonConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_NAME;
	
	/** Page Pokemon attribute filters types */
	public static final String FIELD_FILTERS_TYPES = CommonConstants.FIELD_FILTERS + CommonConstants.DOT + FIELD_TYPES;
	
	
	/**
	 * Default constructor<br>
	 */
	private PokemonConstants() {
	}
}
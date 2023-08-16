package com.heytrade.pokedex.pokemon.model;

import com.heytrade.pokedex.commons.model.Core;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Pokemon type entity
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PokemonType extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 
	 * The name.
	 * @param primaryKey {@link String} The name
	 * @return {@link String} the name
	 */
	private String name;
	
}

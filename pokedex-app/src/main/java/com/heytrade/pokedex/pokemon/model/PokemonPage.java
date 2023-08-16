package com.heytrade.pokedex.pokemon.model;

import com.heytrade.pokedex.commons.model.Page;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Pokemon search filters
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PokemonPage extends Page<Pokemon> {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

}

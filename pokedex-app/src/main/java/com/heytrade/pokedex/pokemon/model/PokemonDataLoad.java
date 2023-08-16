package com.heytrade.pokedex.pokemon.model;

import java.util.List;

import com.heytrade.pokedex.commons.model.CrudDataLoad;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * PokemonDataLoad entity
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PokemonDataLoad extends CrudDataLoad {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * The allowed pokemon types.
	 * @param types {@link List} of {@link PokemonType} The types
	 * @return {@link List} of {@link PokemonType} the types
	 */
	private List<PokemonType> types;

}

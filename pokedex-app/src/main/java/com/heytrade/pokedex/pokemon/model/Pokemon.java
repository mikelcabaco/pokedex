package com.heytrade.pokedex.pokemon.model;

import java.util.List;

import com.heytrade.pokedex.commons.model.Core;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Pokemon entity
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Pokemon extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 
	 * The name.
	 * @param name {@link String} The name
	 * @return {@link String} the name
	 */
	private String name;
	
	/** 
	 * The favourite.
	 * @param favourite {@link boolean} The favourite
	 * @return {@link boolean} the favourite
	 */
	private Boolean favourite;
	
	/** 
	 * The pokemon weight.
	 * @param weight {@link Float} The weight
	 * @return {@link Float} the weight
	 */
	private Float weight;
	
	/** 
	 * The pokemon height.
	 * @param height {@link Float} The weight
	 * @return {@link Float} the weight
	 */
	private Float height;
	
	/** 
	 * The pokemon types.
	 * @param types @{@link List}<{@link PokemonType}> The types
	 * @return @{@link List}<{@link PokemonType}> the types
	 */
	private List<PokemonType> types;
	
	/** 
	 * The pokemon combat power.
	 * @param combatPower {@link Long} The combat power
	 * @return {@link Long} the combat power
	 */
	private Long combatPower;
	
	/** 
	 * The pokemon health points.
	 * @param healthPoints {@link Long} The health points
	 * @return {@link Long} the health points
	 */
	private Long healthPoints;
	
	/** 
	 * The pokemon id from wich evolves.
	 * @param evolutionFrom {@link Long} The evolution from
	 * @return {@link Long} the evolution from
	 */
	private Long evolutionFrom;
	
	/** 
	 * The pokemon evolutions.
	 * @param types @{@link List}<{@link PokemonType}> The evolutions
	 * @return @{@link List}<{@link PokemonType}> the evolutions
	 */
	private List<Pokemon> evolutions;
	
	/** 
	 * The image.
	 * @param image {@link byte}[] The image
	 * @return {@link byte}[] the image
	 */
	private byte[] image;
	
	/** 
	 * The sound.
	 * @param sound {@link byte}[] The sound
	 * @return {@link byte}[] the sound
	 */
	private byte[] sound;
}

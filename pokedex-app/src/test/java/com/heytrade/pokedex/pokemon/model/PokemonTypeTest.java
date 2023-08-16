package com.heytrade.pokedex.pokemon.model;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * Test class for {@link PokemonType}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
class PokemonTypeTest {

	/**
	 * Test method with library equalsVerifier.
	 */
	@Test
	void testPokemonType() {
		var pokemonType = new PokemonType();
		pokemonType.setId(1L);
		pokemonType.setName("Grass");
		
		var pokemonType2 = new PokemonType();
		pokemonType.setId(2L);
		pokemonType.setName("Poison");
		
		EqualsVerifier.forClass(PokemonType.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, pokemonType, pokemonType2)
			.verify();
	}

}

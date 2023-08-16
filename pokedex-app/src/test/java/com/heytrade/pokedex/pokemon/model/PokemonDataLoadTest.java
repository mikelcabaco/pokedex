package com.heytrade.pokedex.pokemon.model;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * Test class for {@link PokemonDataLoad}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
class PokemonDataLoadTest {

	/**
	 * Test method with library equalsVerifier.
	 */
	@Test
	void testPokemonDataLoad() {
		
		var type1 = new PokemonType();
		type1.setId(1L);
		type1.setName("Grass");
		List<PokemonType> types = List.of(type1);
		
		var type2 = new PokemonType();
		type2.setId(2L);
		type2.setName("Poison");
		List<PokemonType> types2 = List.of(type2);
		
		
		EqualsVerifier.forClass(PokemonDataLoad.class)
		.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS, Warning.ALL_FIELDS_SHOULD_BE_USED)
		.withRedefinedSuperclass()
		.withPrefabValues(Object.class, types, types2)
		.verify();
	}

}

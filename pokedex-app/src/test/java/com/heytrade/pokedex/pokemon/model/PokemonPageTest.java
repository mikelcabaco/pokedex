package com.heytrade.pokedex.pokemon.model;

import java.util.List;

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * Test class for {@link PokemonPage}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
class PokemonPageTest {

	/**
	 * Test method with library equalsVerifier.
	 */
	@Test
	void testPokemonPage() {
		var pokemon = new Pokemon();
		var type = new PokemonType();
		type.setId(1L);
		type.setName("Grass");
		pokemon.setTypes(List.of(type));
		
		pokemon.setFavourite(false);
		pokemon.setCombatPower(12L);
		pokemon.setHealthPoints(21L);
		pokemon.setEvolutionFrom(1L);
		pokemon.setHeight(0.44F);
		pokemon.setWeight(15.3F);
		
		
		var pokemon2 = new Pokemon();
		pokemon2.setName("Ivysaur");
		pokemon2.setEvolutions(List.of(pokemon));
		pokemon2.setImage(new byte[] {});
		pokemon2.setSound(new byte[] {});
		
		EqualsVerifier.forClass(PokemonPage.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, pokemon, pokemon2)
			.verify();
	}

}

package com.heytrade.pokedex.audit.model;

import java.time.Instant;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.heytrade.pokedex.audit.enums.AuditableActionsEnum;
import com.heytrade.pokedex.audit.enums.AuditableStepsEnum;
import com.heytrade.pokedex.pokemon.model.Pokemon;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;


/**
 * Test class for {@link Audit}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
class AuditTest {

	/**
	 * Test method with library equalsVerifier.
	 */
	@Test
	void testAudit() {
		var audit = new Audit();
		audit.setId(1L);
		audit.setPrimaryKey("pokemon_id");
		audit.setAction(AuditableActionsEnum.INSERT);
		audit.setStep(AuditableStepsEnum.BEFORE);
		audit.setCreated(Date.from(Instant.now()));
		audit.setTable("pokemons");
		audit.setUser("user");
		audit.setData(new Pokemon());
		
		var audit2 = new Audit();
		audit2.setId(2L);
		audit2.setAction(AuditableActionsEnum.INSERT);
		audit.setStep(AuditableStepsEnum.AFTER);
		
		EqualsVerifier.forClass(Audit.class)
			.suppress(Warning.STRICT_INHERITANCE, Warning.NONFINAL_FIELDS)
			.withRedefinedSuperclass()
			.withPrefabValues(Object.class, audit, audit2)
			.verify();
	}

}

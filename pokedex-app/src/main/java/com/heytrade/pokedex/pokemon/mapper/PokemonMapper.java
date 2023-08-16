package com.heytrade.pokedex.pokemon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.heytrade.pokedex.audit.annotation.Auditable;
import com.heytrade.pokedex.audit.enums.AuditableActionsEnum;
import com.heytrade.pokedex.crud.mapper.CrudMapper;
import com.heytrade.pokedex.pokemon.model.Pokemon;
import com.heytrade.pokedex.pokemon.model.PokemonType;


/**
 * Mapper interface to persist pokemon actions
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Mapper
public interface PokemonMapper extends CrudMapper<Pokemon> {
	
	static final String METHOD_NOT_ALLOWED = "Method not allowed";
	
	
	@Override
	default void insert(Pokemon dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	@Override
	default void delete(Pokemon dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	@Override
	default void update(Pokemon dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	/**
	 * Add and remove a Pokemon to/from your Favourites
	 * @param p {@link Pokemon} the pokemon
	 */
	@Auditable(action = AuditableActionsEnum.UPDATE, table="pokemons", primaryKey="pokemon_id")
	void setOrUnsetFavourite(Pokemon p);
	
	
	/**
	 * Get all available pokemon types.
	 * @return {@link List}<{@link PokemonType}> the pokemon types
	 */
	List<PokemonType> getTypes();
}

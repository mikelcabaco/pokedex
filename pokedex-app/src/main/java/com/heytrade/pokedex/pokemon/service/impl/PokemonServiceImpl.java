package com.heytrade.pokedex.pokemon.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heytrade.pokedex.commons.exceptions.NotFoundException;
import com.heytrade.pokedex.commons.model.DataLoad;
import com.heytrade.pokedex.crud.mapper.CrudMapper;
import com.heytrade.pokedex.pokemon.mapper.PokemonMapper;
import com.heytrade.pokedex.pokemon.model.Pokemon;
import com.heytrade.pokedex.pokemon.model.PokemonDataLoad;
import com.heytrade.pokedex.pokemon.service.PokemonService;


/**
 * Pokemon service implementation to handle pokemons
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Service
public class PokemonServiceImpl implements PokemonService {

	/** The associated mapper */
	@Autowired
	private PokemonMapper pokemonMapper;


	@Override
	public CrudMapper<Pokemon> getMapper() {
		return pokemonMapper;
	}
	
	
	@Override
	public Pokemon setOrUnsetFavourite(Pokemon p) throws NotFoundException {
		// Get pokemon
		var pokemon = this.find(p);
		pokemon.setFavourite(!pokemon.getFavourite());
		
		// Update pokemon favourite property
		pokemonMapper.setOrUnsetFavourite(pokemon);
		
		return pokemon;
	}


	@Override
	public DataLoad initPage() {
		var data = new PokemonDataLoad();
		data.setTypes(pokemonMapper.getTypes());
		return data;
	}

}
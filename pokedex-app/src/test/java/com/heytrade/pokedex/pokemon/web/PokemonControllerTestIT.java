package com.heytrade.pokedex.pokemon.web;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.heytrade.BaseTest;
import com.heytrade.pokedex.common.constants.CommonConstants;
import com.heytrade.pokedex.crud.constants.CrudConstants;
import com.heytrade.pokedex.crud.service.CrudService;
import com.heytrade.pokedex.exceptions.model.ApiError;
import com.heytrade.pokedex.i18n.constants.I18nConstants;
import com.heytrade.pokedex.pokemon.constants.PokemonConstants;
import com.heytrade.pokedex.pokemon.model.Pokemon;
import com.heytrade.pokedex.pokemon.model.PokemonDataLoad;
import com.heytrade.pokedex.pokemon.model.PokemonPage;
import com.heytrade.pokedex.pokemon.model.PokemonType;


/**
 * Test pokemon module
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
class PokemonControllerTestIT extends BaseTest {

	/**
	 * Test method to get all available pokemon types to show in pokemon type combo and validate data received when search.
	 */
	@Test
	void testInitPage() throws Exception {
		var dto = new Pokemon();
		dto.setId(1111L); // Undefined
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INIT_PAGE);
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		var data = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<PokemonDataLoad>() {});
		Assertions.assertNotNull(data);
		Assertions.assertNotNull(data.getTypes());
		Assertions.assertTrue(data.getTypes().size() > 0);
	}
	
	/**
	 * Test search pokemons without any filter. Get all pokemons.
	 */
	@Test
	void testSearchWithoutFilters() throws Exception {
		var dto = new PokemonPage();
		dto.setFilters(new Pokemon());

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		var result = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<PokemonPage>() {});
		Assertions.assertNotNull(result);
		Assertions.assertEquals(9, result.getTotalRecords());
		Assertions.assertEquals(9, result.getRecords().size());
	}
	
	/**
	 * Test search pokemon by text. Send filter name='Bulbasaur' and check that only get one.
	 */
	@Test
	void testSearchWithFilterNameBulbasaur() throws Exception {
		var dto = new PokemonPage();
		dto.setFilters(new Pokemon());
		dto.getFilters().setName("Bulbasaur");
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
			.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");

		ResultActions ra = mockMvc.perform(requestBuilder);

		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		var result = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<PokemonPage>() {});
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1, result.getTotalRecords());
		Assertions.assertEquals(1, result.getRecords().size());
	}
	
	/**
	 * Test search pokemon by text. Send filter name='Tle' and check that only get pokemons with name 'Squirtle' and 'Wartortle'.
	 */
	@Test
	void testSearchWithFilterNameTle() throws Exception {
		var dto = new PokemonPage();
		dto.setFilters(new Pokemon());
		dto.getFilters().setName("Tle");
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		var result = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<PokemonPage>() {});
		Assertions.assertNotNull(result);
		Assertions.assertEquals(2, result.getTotalRecords());
		Assertions.assertEquals(2, result.getRecords().size());
	}
	
	/**
	 * Test search pokemon by type. Send filter types with one type_id=1 and check that get pokemon 'Bulbasaur' and his evolutions.
	 */
	@Test
	void testSearchWithFilterTypeGrass() throws Exception {
		var dto = new PokemonPage();
		dto.setFilters(new Pokemon());
		
		var type = new PokemonType();
		type.setId(1L); // Grass
		dto.getFilters().setTypes(List.of(type));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		var result = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<PokemonPage>() {});
		Assertions.assertNotNull(result);
		Assertions.assertEquals(3, result.getTotalRecords());
		Assertions.assertEquals(3, result.getRecords().size());
	}
	
	/**
	 * Test search pokemon by type. Send filter types with one type_id=6 and check that not get any pokemon.
	 */
	@Test
	void testSearchWithFilterTypeWithoutPokemons() throws Exception {
		var dto = new PokemonPage();
		dto.setFilters(new Pokemon());
		
		var type = new PokemonType();
		type.setId(6L); // Bug
		dto.getFilters().setTypes(List.of(type));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		
		var result = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<PokemonPage>() {});
		Assertions.assertNotNull(result);
		Assertions.assertEquals(0, result.getTotalRecords());
		Assertions.assertEquals(0, result.getRecords().size());
	}
	
	/**
	 * Test search pokemon by type. Send filter types with one type_id=116 and check that request are invalid and error is type is invalid.
	 */
	@Test
	void testSearchWithFilterTypeNotAllowed() throws Exception {
		var dto = new PokemonPage();
		dto.setFilters(new Pokemon());
		
		var type = new PokemonType();
		type.setId(116L); // Undefined
		dto.getFilters().setTypes(List.of(type));
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_SEARCH )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		var error = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiError>() {});
		Assertions.assertNotNull(error);
		var errors = error.getErrors();
		Assertions.assertNotNull(errors);
		Assertions.assertTrue(errors.size() > 0);
		var errorFirst = errors.get(0);
		Assertions.assertTrue(errorFirst.containsKey(I18nConstants.I18N_VALIDATION_FIELD_INVALID));
		var fields = errorFirst.get(I18nConstants.I18N_VALIDATION_FIELD_INVALID);
		Assertions.assertTrue(fields.contains(I18nConstants.I18N_PREFIX.concat("pokemonPage.").concat(PokemonConstants.FIELD_FILTERS_TYPES)));
	}
	
	/**
	 * Test find pokemon. Send object without id and check that request are invalid and error is id is required.
	 */
	@Test
	void testFindWithoutId() throws Exception {
		var dto = new Pokemon();
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isBadRequest());
		var error = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiError>() {});
		Assertions.assertNotNull(error);
		var errors = error.getErrors();
		Assertions.assertNotNull(errors);
		Assertions.assertTrue(errors.size() > 0);
		var errorFirst = errors.get(0);
		Assertions.assertTrue(errorFirst.containsKey(I18nConstants.I18N_VALIDATION_FIELD_REQUIRED));
		var fields = errorFirst.get(I18nConstants.I18N_VALIDATION_FIELD_REQUIRED);
		Assertions.assertTrue(fields.contains(I18nConstants.I18N_PREFIX.concat("pokemon.").concat(CommonConstants.FIELD_ID)));
	}
	
	/**
	 * Test find pokemon. Send object with not exists id and check that request status is NOT_FOUND.
	 */
	@Test
	void testFindWithPokemonNotExists() throws Exception {
		var dto = new Pokemon();
		dto.setId(99999L); // Undefined
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isNotFound());
		var error = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiError>() {});
		Assertions.assertNotNull(error);
		Assertions.assertEquals(CrudService.RECORD_NOT_FOUND, error.getMessage());
		Assertions.assertEquals(HttpStatus.NOT_FOUND, error.getStatus());
	}
	
	/**
	 * Test find pokemon. Send object with 'Bulbasaur' id and check that get pokemon 'Bulbasaur' and his evolutions.
	 */
	@Test
	void testFind() throws Exception {
		var dto = new Pokemon();
		dto.setId(1L); // Bulbasaur
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		var result = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<Pokemon>() {});
		Assertions.assertNotNull(result);
		Assertions.assertEquals(1L, result.getId());
		Assertions.assertNotNull(result.getEvolutions());
		Assertions.assertEquals(2, result.getEvolutions().size());
	}
	
	/**
	 * Test add or remove a pokemon to/from favourites. Send object with 'Bulbasaur' id and check that pokemon 'Bulbasaur'
	 * has added or removed from favourites.
	 */
	@Test
	void testAddToFavourites() throws Exception {
		// Get Bulbasaur pokemon
		var dto = new Pokemon();
		dto.setId(1L); // Bulbasaur
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_FIND )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isOk());
		var initial = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<Pokemon>() {});
		
		
		// Add/Remove to favourite
		requestBuilder = MockMvcRequestBuilders.post(
				PokemonConstants.CONTROLLER_NAMESPACE + PokemonConstants.CONTROLLER_FAVOURITES_POKEMONS )
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isAccepted());
		var result = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<Pokemon>() {});
		
		Assertions.assertEquals(!result.getFavourite(), initial.getFavourite());
	}
	
	/**
	 * Test insert pokemon. Check that method are locked.
	 */
	@Test
	void testInsert() throws Exception {
		var dto = new Pokemon();
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_INSERT)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isLocked());
		var error = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiError>() {});
		Assertions.assertNotNull(error);
		Assertions.assertNull(error.getErrors());
		Assertions.assertEquals(HttpStatus.LOCKED, error.getStatus());
		Assertions.assertEquals(I18nConstants.I18N_VALIDATION_METHOD_LOCKED, error.getMessage());
	}
	
	/**
	 * Test update pokemon. Check that method are locked.
	 */
	@Test
	void testUpdate() throws Exception {
		var dto = new Pokemon();
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_UPDATE)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isLocked());
		var error = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiError>() {});
		Assertions.assertNotNull(error);
		Assertions.assertNull(error.getErrors());
		Assertions.assertEquals(HttpStatus.LOCKED, error.getStatus());
		Assertions.assertEquals(I18nConstants.I18N_VALIDATION_METHOD_LOCKED, error.getMessage());
	}
	
	/**
	 * Test delete pokemon. Check that method are locked.
	 */
	@Test
	void testDelete() throws Exception {
		var dto = new Pokemon();
		
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post(
				PokemonConstants.CONTROLLER_NAMESPACE + CrudConstants.CRUD_CONTROLLER_DELETE)
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto));
		
		this.login(requestBuilder, "heytrade");
		
		ResultActions ra = mockMvc.perform(requestBuilder);
		
		ra.andExpect(MockMvcResultMatchers.status().isLocked());
		var error = mapper.readValue(ra.andReturn().getResponse().getContentAsString(), new TypeReference<ApiError>() {});
		Assertions.assertNotNull(error);
		Assertions.assertNull(error.getErrors());
		Assertions.assertEquals(HttpStatus.LOCKED, error.getStatus());
		Assertions.assertEquals(I18nConstants.I18N_VALIDATION_METHOD_LOCKED, error.getMessage());
	}
}

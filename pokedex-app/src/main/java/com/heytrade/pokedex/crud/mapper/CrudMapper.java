package com.heytrade.pokedex.crud.mapper;


import com.heytrade.pokedex.commons.exceptions.NotFoundException;
import com.heytrade.pokedex.commons.model.Core;
import com.heytrade.pokedex.commons.model.Page;


/**
 * Generic interface implementing generic method for CRUD operations.
 * 
 * @param <T> Any object extending {@link Core}
 * @author HEYTRADE
 * @since 1.0.0
 */
public interface CrudMapper<T extends Core<?>> {

	/**
	 * Persist a new record of T 
	 * 
	 * @param dto the record to persist
	 */
	void insert(T dto);
	
	/**
	 * Updates an existing record
	 * 
	 * @param dto the record to update
	 */
	void update(T dto);
	
	/**
	 * Delete an existing record
	 * 
	 * @param dto the record to delete
	 */
	void delete(T dto);
	
	/**
	 * Find a record of T
	 * 
	 * @param dto the record to find
	 * @return T record
	 */
	T find(T dto) throws NotFoundException;
	
	/**
	 * Find all records of T
	 * 
	 * @param dto with search criteria
	 * @return {@link Page} of T that meets the search criteria
	 */
	Page<T> search(Page<T> dto);
}

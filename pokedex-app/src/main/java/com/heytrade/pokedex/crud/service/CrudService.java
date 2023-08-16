package com.heytrade.pokedex.crud.service;

import org.springframework.transaction.annotation.Transactional;

import com.heytrade.pokedex.commons.exceptions.NotFoundException;
import com.heytrade.pokedex.commons.model.Core;
import com.heytrade.pokedex.commons.model.Page;
import com.heytrade.pokedex.crud.mapper.CrudMapper;


/**
 * Generic service interface that implements generic method for CRUD operations.<br>
 * 
 * All methods in this interfaces are marked as @{@link Transactional}
 * 
 * @param <T> Any object extending {@link Core}
 * @author HEYTRADE
 * @since 1.0.0
 */
@Transactional
public interface CrudService<T extends Core<?>> extends Initializable {
	static final String RECORD_NOT_FOUND = "Record not found";
	
	/**
	 * Get the Mapper class associated with the service implementation.
	 * 
	 * @return {@link CrudMapper} of T inside the service
	 */
	CrudMapper<T> getMapper();
	
	
	/**
	 * Persist a new record of T 
	 * 
	 * @param dto the record to persist
	 */
	public default void insert(T dto) {
		this.getMapper().insert(dto);
	}
	
	/**
	 * Updates an existing record
	 * 
	 * @param dto the record to update
	 */
	public default void update(T dto) {
		this.getMapper().update(dto);
	}
	
	/**
	 * Delete an existing record
	 * 
	 * @param dto the record to delete
	 */
	public default void delete(T dto) {
		this.getMapper().delete(dto);
	}
	
	/**
	 * Find a record of T
	 * 
	 * @param dto the record to find
	 * @return T record
	 * @throws NotFoundException 
	 */
	public default T find(T dto) throws NotFoundException {
		var result = this.getMapper().find(dto);
		if (null == result) {
			throw new NotFoundException(RECORD_NOT_FOUND);
		}
		
		return result;
	}
	
	/**
	 * Find all records of T
	 * 
	 * @param dto with search criteria
	 * @return {@link Page} of T that meets the search criteria
	 */
	public default Page<T> search(Page<T> dto) {
		var result = this.getMapper().search(dto);
		if (null == result) {
			result = newInstance(dto);
		}
		return result;
	}
	
	
	/**
	 * Get a new instance when search has no results.
	 * @param dto {@link Page}&lt;T&gt;
	 * @return new instance
	 */
	@SuppressWarnings("unchecked")
	private Page<T> newInstance(Page<T> dto) {
		Page<T> instance = null;
		try {
			instance = dto.getClass().getConstructor().newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return instance;
	}

}

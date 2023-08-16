package com.heytrade.pokedex.commons.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;


/**
 * Core dto to implement search filters of other application entities
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
public abstract class Page<E extends Core<?>> implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Total number of records.
	 * @param totalRecords {@link Integer} The totalRecords
	 * @return {@link Integer} the totalRecords
	 */
	private int totalRecords;
	
	/** 
	 * Obtained records from search.
	 * @param records {@link List} The records
	 * @return {@link List} the records
	 */
	private List<E> records = new ArrayList<>();
	
	/** 
	 * The dto for which this filter is used.
	 * @param filters The filters
	 * @return the filters
	 */
	private E filters;
}

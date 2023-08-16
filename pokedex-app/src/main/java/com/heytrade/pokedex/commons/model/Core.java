package com.heytrade.pokedex.commons.model;

import java.io.Serializable;

import lombok.Data;


/**
 * Core entity for application, all others entities should extends this.<br>
 * 
 * Uses a generic type <code>E</code> to determine the id field
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
public abstract class Core<E extends Serializable> implements Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** 
	 * Unique entity ID.
	 * @param id The id
	 * @return the id
	 */
	private E id;
}

package com.heytrade.pokedex.commons.model;

import java.io.Serializable;

import lombok.Data;


/**
 * Abstract class to store the initial data loaded in services
 * 
 * @author HEYTRADE
 * @version 1.0.0
 */
@Data
public abstract class CrudDataLoad implements DataLoad, Serializable {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
}

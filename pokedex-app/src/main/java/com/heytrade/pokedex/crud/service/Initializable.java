package com.heytrade.pokedex.crud.service;

import com.heytrade.pokedex.commons.model.DataLoad;


/**
 * Initializable interface
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
public interface Initializable {
	
	/**
	 * Prepare the data used when user access the search form.<br>
	 * Should populate buttons, columns, combos and other elements to feed the front application.
	 * 
	 * @return {@link DataLoad} the object with all data needed
	 */
	public default DataLoad initPage() {
		return null;
	}
	
}

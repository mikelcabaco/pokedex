package com.heytrade.pokedex.audit.model;

import java.util.Date;

import com.heytrade.pokedex.audit.enums.AuditableActionsEnum;
import com.heytrade.pokedex.audit.enums.AuditableStepsEnum;
import com.heytrade.pokedex.commons.model.Core;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Audit to handle audit data
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Audit extends Core<Long> {
	
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** 
	 * The table.
	 * @param table {@link String} The table
	 * @return {@link String} the table
	 */
	private String table;
	
	/** 
	 * The auditable table primary key.
	 * @param primaryKey {@link String} The primaryKey
	 * @return {@link String} the primaryKey
	 */
	private String primaryKey;
	
	/** 
	 * The auditable action.
	 * @param action {@link AuditableActionsEnum} The action
	 * @return {@link AuditableActionsEnum} the action
	 */
	private AuditableActionsEnum action;
	
	/** 
	 * The auditable step.
	 * @param step {@link AuditableStepsEnum} The step
	 * @return {@link AuditableStepsEnum} the step
	 */
	private AuditableStepsEnum step;
	
	/** 
	 * The user performing the action to audit.
	 * @param user {@link String} The user
	 * @return {@link String} the user
	 */
	private String user;
	
	/** 
	 * The record to audit.
	 * @param data {@link Core} The record
	 * @return {@link Core} the record
	 */
	private Core<?> data;
	
	/** 
	 * Creation date of the audit.
	 * @param created {@link Date} The created
	 * @return {@link Date} the created
	 */
	private Date created;
	
}

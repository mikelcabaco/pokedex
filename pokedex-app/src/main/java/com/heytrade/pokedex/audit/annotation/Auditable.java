package com.heytrade.pokedex.audit.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import com.heytrade.pokedex.audit.enums.AuditableActionsEnum;


/**
 * Auditable annotation
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Documented
@Retention(RUNTIME)
@Target({ METHOD })
public @interface Auditable {

	/**
	 * Table where the audit will be persisted. Resulting table will be <code>table</code>$AUD.
	 * @return {@link String} the table
	 * @see com.heytrade.pokedex.audit.constants.AuditableConstants#AUD_SUFFIX
	 */
	String table();
	
	/** 
	 * The primary key of the table
	 * @return {@link String} the table primary key
	 */
	String primaryKey();
	
	/**
	 * The action that will be audited
	 * @return {@link AuditableActionsEnum} the action
	 */
	AuditableActionsEnum action();
}

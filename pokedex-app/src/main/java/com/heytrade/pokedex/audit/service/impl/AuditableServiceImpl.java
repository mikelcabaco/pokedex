package com.heytrade.pokedex.audit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.heytrade.pokedex.audit.mapper.AuditableMapper;
import com.heytrade.pokedex.audit.model.Audit;
import com.heytrade.pokedex.audit.service.AuditableService;
import com.heytrade.pokedex.crud.mapper.CrudMapper;


/**
 * Implementation of audit service
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Service
public class AuditableServiceImpl implements AuditableService {

	/** The audit mapper. */
	@Autowired
	private AuditableMapper auditMapper;
	
	
	@Override
	public CrudMapper<Audit> getMapper() {
		return this.auditMapper;
	}
	
}

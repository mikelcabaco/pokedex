package com.heytrade.pokedex.audit.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.heytrade.pokedex.audit.model.Audit;
import com.heytrade.pokedex.commons.model.Page;
import com.heytrade.pokedex.crud.mapper.CrudMapper;


/**
 * Auditable interface to persist auditable actions
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Mapper
public interface AuditableMapper extends CrudMapper<Audit> {
	
	static final String METHOD_NOT_ALLOWED = "Method not allowed";
	
	
	@Override
	default Page<Audit> search(Page<Audit> dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	@Override
	default void delete(Audit dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	@Override
	default void update(Audit dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
	
	@Override
	default Audit find(Audit dto) {
		throw new UnsupportedOperationException(METHOD_NOT_ALLOWED);
	}
}

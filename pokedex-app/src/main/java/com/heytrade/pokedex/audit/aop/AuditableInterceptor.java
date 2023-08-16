package com.heytrade.pokedex.audit.aop;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import com.heytrade.pokedex.audit.annotation.Auditable;
import com.heytrade.pokedex.audit.enums.AuditableStepsEnum;
import com.heytrade.pokedex.audit.model.Audit;
import com.heytrade.pokedex.audit.service.AuditableService;
import com.heytrade.pokedex.commons.model.Core;

import lombok.extern.slf4j.Slf4j;


/**
 * Auditable interceptor for all methods annotated with {@link Auditable}
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Slf4j
public class AuditableInterceptor implements MethodInterceptor {
	
	/** The auditable service */
	@Autowired
	private AuditableService auditableService;
	
	/**
	 * Create a new audit record with previous data before call the annotated method 
	 * and a second record with the data after call the annotated method.
	 * 
	 * @param invocation MethodInvocation with interceptor data
	 * @return Object intercepted method result
	 * @throws Throwable if any error
	 */
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Audit auditDto = null;
		try {
			var auditable = AnnotationUtils.findAnnotation(invocation.getMethod(), Auditable.class);
			
			Core<?> data = getData(invocation.getArguments());
			auditDto = getAudit(auditable, data);
			
			//Saves the audit record before the action
			auditableService.insert(auditDto);
		} catch (Exception e) {
			log.error("Error ocurred before audit.", e);
		}

		Object result = invocation.proceed();
		
		try {
			if (null != auditDto) {
				//Update the record inside auditDto
				if (result instanceof Core<?>) {
					auditDto.setData((Core<?>) result);
				}
				
				//Set the next step to perform
				auditDto.setStep(AuditableStepsEnum.AFTER);
				
				//Saves the audit record after the action
				auditableService.insert(auditDto);
			}	
		} catch (Exception e) {
			log.error("Error ocurred after audit.", e);
		}

	    return result;
	}
	
	/**
	 * Get the parameter at specified index.<br>
	 * If null or invalid iterates all parameters to find the object that extends {@link Core}.
	 * 
	 * @param args The arguments of the intercepted method.
	 * @param index {@link Byte} the index where the object should be.
	 * @return {@link Core} the record to audit or null if not found
	 */
	private Core<?> getData(Object[] args) {
		Core<?> data = null;
		for (Object arg : args) {
			if (arg instanceof Core<?> coreData) {
				data = coreData;
				break;
			}
		}
		return data;
	}
		
	private Audit getAudit(Auditable auditable, Core<?> data) {
		var auditDto = new Audit();
		auditDto.setTable(auditable.table());
		auditDto.setPrimaryKey(auditable.primaryKey());
		auditDto.setAction(auditable.action());
		auditDto.setStep(AuditableStepsEnum.BEFORE);
		auditDto.setUser(SecurityContextHolder.getContext().getAuthentication().getName());
		auditDto.setData(data);
		auditDto.setCreated(Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant()));
		
		return auditDto;
	}
}

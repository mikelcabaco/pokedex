package com.heytrade.pokedex.audit.aop;

import java.lang.reflect.Method;

import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.StaticMethodMatcherPointcut;
import org.springframework.core.annotation.AnnotationUtils;

import com.heytrade.pokedex.audit.annotation.Auditable;

import lombok.EqualsAndHashCode;


/**
 * AuditableAdvisor to catch {@link Auditable} annotation over interfaces
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = false)
public class AuditableAdvisor extends AbstractPointcutAdvisor {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	/** The method interceptor */
	private final transient MethodInterceptor interceptor;
	
	/** Auditable pointcut */
	private final transient StaticMethodMatcherPointcut pointcut;

	/**
	 * Constructor.
	 * @param auditableInterceptor {@link AuditableInterceptor} the interceptor to use
	 * @param excluded {@link String} array of excluded classes and methods to be audited.
	 */
	public AuditableAdvisor(AuditableInterceptor auditableInterceptor) {
		super();
		this.interceptor = auditableInterceptor;
		this.pointcut = new AuditAnnotationPointcut();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.PointcutAdvisor#getPointcut()
	 */
	@Override
	public Pointcut getPointcut() {
		return this.pointcut;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.aop.Advisor#getAdvice()
	 */
	@Override
	public Advice getAdvice() {
		return this.interceptor;
	}

	/**
	 * A matcher that matches:
	 * <ul>
	 * <li>A method on a class annotated with {@link Auditable}.</li>
	 * <li>A method on a class extending another class annotated with {@link Auditable}.</li>
	 * <li>A method on a class implementing an interface annotated with {@link Auditable}.</li>
	 * <li>A method implementing a method in a interface annotated with {@link Auditable}.</li>
	 * </ul>
	 * <p>
	 * <strong>Note:</strong> this uses springs utils to find the annotation and will not be portable outside the spring environment.
	 * </p>
	 */
	private final class AuditAnnotationPointcut extends StaticMethodMatcherPointcut {
		
		/*
		 * (non-Javadoc)
		 * 
		 * @see org.springframework.aop.support.StaticMethodMatcher.matches(Method, Class<?>, Object...)
		 */
		@Override
		public boolean matches(Method method, Class<?> targetClass) {
			return null != AnnotationUtils.findAnnotation(method, Auditable.class);
		}
	}
}
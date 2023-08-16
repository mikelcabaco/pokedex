package com.heytrade.pokedex.common.utils;

import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;

import com.heytrade.pokedex.common.constants.CommonConstants;
import com.heytrade.pokedex.commons.model.Core;
import com.heytrade.pokedex.i18n.constants.I18nConstants;

import lombok.extern.slf4j.Slf4j;


/**
 * Validation util class extending {@link org.springframework.validation.ValidationUtils}
 * with more validation methods for dates, ranges, sizes...
 * 
 * @author HEYTRADE
 * @since 1.0.0
 */
@Slf4j
public abstract class ValidatorUtils extends org.springframework.validation.ValidationUtils {
	
	private static final String ERRORS_OBJECT_MUST_NOT_BE_NULL = "Errors object must not be null";

	/** Private constructor */
	private ValidatorUtils() {
	}
	
	/**
	 * Reject the given field if the value length is higher than <code>maxLength</code> parameter.
	 * @param errors the {@code Errors} instance to register errors on
	 * @param field the field name to check
	 * @param maxLength the maximum length allowed for the field
	 */
	public static void rejectIfLengthExceeded(Errors errors, String field, long maxLength) {
		rejectIfLengthExceeded(errors, field, maxLength, I18nConstants.I18N_VALIDATION_FIELD_MAXLENGTH,
				new Object[] {getFullFieldName(errors, field), maxLength}, null);
	}
	
	/**
	 * Reject the given field with the given error code, error arguments
	 * and default message if the value length is higher than <code>maxLength</code> parameter.
	 * @param errors the {@code Errors} instance to register errors on
	 * @param field the field name to check
	 * @param maxLength the maximum length allowed for the field
	 * @param errorCode the error code, interpretable as message key
	 * @param errorArgs the error arguments, for argument binding via MessageFormat
	 * (can be {@code null})
	 * @param defaultMessage fallback default message
	 */
	public static void rejectIfLengthExceeded(Errors errors, String field, long maxLength, String errorCode,
			@Nullable Object[] errorArgs, @Nullable String defaultMessage) {

		Assert.notNull(errors, ERRORS_OBJECT_MUST_NOT_BE_NULL);
		Object value = errors.getFieldValue(field);
		if (value != null && value.toString().length() > maxLength) {
			errors.rejectValue(field, errorCode, errorArgs, defaultMessage);
		}
	}
	
	
	/**
	 * Reject the given field with the given error code if the value is empty
	 * or just contains whitespace.
	 * <p>An 'empty' value in this context means either {@code null},
	 * the empty string "", or consisting wholly of whitespace.
	 * <p>The object whose field is being validated does not need to be passed
	 * in because the {@link Errors} instance can resolve field values by itself
	 * (it will usually hold an internal reference to the target object).
	 * @param errors the {@code Errors} instance to register errors on
	 * @param field the field name to check
	 */
	public static void rejectIfEmptyOrWhitespace(Errors errors, String field) {
		rejectIfEmptyOrWhitespace(errors, field, I18nConstants.I18N_VALIDATION_FIELD_REQUIRED, new Object[] {getFullFieldName(errors, field)}, null);
	}
	
	
	/**
	 * Reject the given field if the value or values of the field (if is a {@link Collection} is not present in <code>combos</code> collection.
	 * 
	 * @param errors the {@code Errors} instance to register errors
	 * @param field the field name to check
	 * @param val the value to check
	 * @param propertyName {@link String} the property name inside field where value to validate is stored
	 * @param combos {@link Collection} to validate field values
	 */
	public static void rejectIfCollectionNotContains(Errors errors, String field,
			String propertyName, Collection<? extends Core<?>> combos) {
		
		Object value = errors.getFieldValue(field);
		if (value instanceof Collection) {
			for (Object v : (Collection<?>) value) {
				rejectIfCollectionNotContains(errors, field, combos, getPropertyValue(propertyName, v));
			}
		} else {
			rejectIfCollectionNotContains(errors, field, combos, value);
		}
	}
	
	/**
	 * Reject the given field with the given error code, error arguments and default message if the value is not present in <code>combos</code> collection.
	 * 
	 * @param errors the {@code Errors} instance to register errors
	 * @param field the field name to check
	 * @param combos {@link Collection} to validate field values
	 * @param value the value
	 */
	public static void rejectIfCollectionNotContains(Errors errors, String field, Collection<? extends Core<?>> combos, Object value) {
		
		Assert.notNull(errors, ERRORS_OBJECT_MUST_NOT_BE_NULL);
		Assert.notNull(combos, "Collection must not be null");
		
		if (value != null) {
			Predicate<? super String> filter = key -> key.equalsIgnoreCase(value.toString());
			Function<? super Core<?>, String> filterBy = core -> core.getId().toString();
			
			if (combos.stream().map(filterBy).noneMatch(filter)) {
				errors.rejectValue(field, I18nConstants.I18N_VALIDATION_FIELD_INVALID,
						new Object[] {getFullFieldName(errors, field)}, null);
			}
		}
	}
	
	
	/**
	 * Return the full path access to field
	 * <br>
	 * It should be: i18n. + className. + field
	 * 
	 * @param errors {@link Errors} containing the objectName
	 * @param field {@link String} the field name
	 * @return {@link String} composed field for validation
	 */
	private static String getFullFieldName(Errors errors, String field) {
		return I18nConstants.I18N_PREFIX + errors.getObjectName() + CommonConstants.DOT + field;
	}
	
	
	/**
	 * Try to get the value under propertyName inside object.
	 * If propertyName is null or cannot get propertyName then the object is returned
	 * 
	 * @param propertyName {@link String} the property to lookup in object
	 * @param object {@link Object} the object where property is located (could be in nested objects)
	 * @return Property value or the parameter object
	 */
	private static Object getPropertyValue(String propertyName, Object object) {
		if (null == propertyName) {
			return object;
		}
		try {
			return PropertyUtils.getNestedProperty(object, propertyName);
		} catch (Exception e) {
			log.info("Cannot access to request property. Returning default object", e);
			return object;
		}
	}
}
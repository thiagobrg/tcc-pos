package br.com.finance.api.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@Constraint(validatedBy = UniqueTransactionCategoryValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE })
public @interface UniqueTransactionCategory {
	
	public String message() default "There is already category with this name!";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};

}
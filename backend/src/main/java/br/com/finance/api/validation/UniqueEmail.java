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
@Constraint(validatedBy = UniqueEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface UniqueEmail {
	
	public String message() default "There is already user with this username!";
	
	public Class<?>[] groups() default {};
	
	public Class<? extends Payload>[] payload() default{};

}
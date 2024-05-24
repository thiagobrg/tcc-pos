package br.com.finance.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.finance.domain.service.UserService;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

	@Autowired
	private UserService userService;

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		return value != null && !userService.isEmailAlreadyInUse(value);
	}
	
}
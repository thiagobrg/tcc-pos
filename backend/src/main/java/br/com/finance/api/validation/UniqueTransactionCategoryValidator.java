package br.com.finance.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.finance.api.dto.TransactionCategoryDTO;
import br.com.finance.domain.service.TransactionCategoryService;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class UniqueTransactionCategoryValidator implements ConstraintValidator<UniqueTransactionCategory, TransactionCategoryDTO> {

	@Autowired
	private TransactionCategoryService transactionCategoryService;

	@Override
	public boolean isValid(TransactionCategoryDTO value, ConstraintValidatorContext context) {
		return value != null && !transactionCategoryService.existsCategoryNameByUserId(value);
	}
	
}
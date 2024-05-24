package br.com.finance.api.dto;

import java.math.BigDecimal;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class TransactionValueByCategoryDTO {
	
	private BigDecimal value;
	private String categoryName;
	
	public BigDecimal getValue() {
		return value;
	}
	public void setValue(BigDecimal value) {
		this.value = value;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}

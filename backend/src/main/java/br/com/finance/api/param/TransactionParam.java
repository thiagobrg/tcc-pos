package br.com.finance.api.param;

import java.time.LocalDate;

/**
 * Classe representa os parametros de busca para uma Transaction,
 * nenhum é obrigatorio.
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class TransactionParam {

	public Long id;
	public Long userId;
	public String description;
	public LocalDate startDate;
	public LocalDate finalDate;
	public Long categoryId;
	public String type;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public LocalDate getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}
	public LocalDate getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(LocalDate finalDate) {
		this.finalDate = finalDate;
	}
	public Long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}

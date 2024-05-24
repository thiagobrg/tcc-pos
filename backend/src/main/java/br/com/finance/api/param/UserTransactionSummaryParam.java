package br.com.finance.api.param;

import java.time.LocalDate;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class UserTransactionSummaryParam {
	
	private LocalDate date;

	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
}

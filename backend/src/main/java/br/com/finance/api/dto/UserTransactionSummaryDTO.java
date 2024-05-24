package br.com.finance.api.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class UserTransactionSummaryDTO {
	
	private BigDecimal monthlyValue;
	private BigDecimal overallValue;
	private List<TransactionDTO> transactionList;
	
	public BigDecimal getMonthlyValue() {
		return monthlyValue;
	}
	public void setMonthlyValue(BigDecimal monthlyValue) {
		this.monthlyValue = monthlyValue;
	}
	public BigDecimal getOverallValue() {
		return overallValue;
	}
	public void setOverallValue(BigDecimal overallValue) {
		this.overallValue = overallValue;
	}
	public List<TransactionDTO> getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(List<TransactionDTO> transactionList) {
		this.transactionList = transactionList;
	}
}

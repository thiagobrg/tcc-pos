package br.com.finance.api.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public class HomeDashboardDTO {
	
	private BigDecimal overallValue;
	private BigDecimal monthlyValue;
	private BigDecimal incomeValue;
	private BigDecimal outgoingValue;
	private List<TransactionValueByCategoryDTO> transactions;
	
	public BigDecimal getOverallValue() {
		return overallValue;
	}
	public void setOverallValue(BigDecimal overallValue) {
		this.overallValue = overallValue;
	}
	public BigDecimal getMonthlyValue() {
		return monthlyValue;
	}
	public void setMonthlyValue(BigDecimal monthlyValue) {
		this.monthlyValue = monthlyValue;
	}
	public BigDecimal getIncomeValue() {
		return incomeValue;
	}
	public void setIncomeValue(BigDecimal incomeValue) {
		this.incomeValue = incomeValue;
	}
	public BigDecimal getOutgoingValue() {
		return outgoingValue;
	}
	public void setOutgoingValue(BigDecimal outgoingValue) {
		this.outgoingValue = outgoingValue;
	}
	public List<TransactionValueByCategoryDTO> getTransactions() {
		return transactions;
	}
	public void setTransactions(List<TransactionValueByCategoryDTO> transactions) {
		this.transactions = transactions;
	}
}

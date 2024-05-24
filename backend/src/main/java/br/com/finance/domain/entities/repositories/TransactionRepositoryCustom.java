package br.com.finance.domain.entities.repositories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import br.com.finance.api.param.TransactionParam;
import br.com.finance.domain.entities.Transaction;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
public interface TransactionRepositoryCustom {
	
	/**
	 * Realiza a busca por transactions pelos parametros.<br>
	 * <br>
	 * OBS: Nenhum parametro é obrigatorio.
	 * @param param
	 * @return
	 */
	List<Transaction> findByParam(TransactionParam param);

	/**
	 * Retorna o valor total das transacoes do Usuario.
	 * 
	 * @param userId
	 * @param dateTo 
	 * @return
	 */
	BigDecimal getOverallValue(Long userId, LocalDate dateTo);

	/**
	 * Retorna o valor total das transacoes do usuario do periodo.
	 * 
	 * @param userId
	 * @param date
	 * @param dateFrom 
	 * @return
	 */
	BigDecimal getValueByPeriod(Long userId, LocalDate dateTo, LocalDate dateFrom);
}

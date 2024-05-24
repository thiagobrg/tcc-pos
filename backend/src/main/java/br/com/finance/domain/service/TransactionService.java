package br.com.finance.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.finance.api.dto.HomeDashboardDTO;
import br.com.finance.api.dto.TransactionDTO;
import br.com.finance.api.dto.TransactionValueByCategoryDTO;
import br.com.finance.api.dto.UserTransactionSummaryDTO;
import br.com.finance.api.param.TransactionParam;
import br.com.finance.domain.entities.Transaction;
import br.com.finance.domain.entities.Transaction.TransactionTypeEnum;
import br.com.finance.domain.entities.TransactionCategory;
import br.com.finance.domain.entities.User;
import br.com.finance.domain.entities.repositories.TransactionRepository;
import br.com.finance.utils.DateUtils;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public boolean existsById(Long transactionId) { 
		return transactionRepository.existsById(transactionId);
	}
	
	public void delete(Long transactionId) {
		transactionRepository.deleteById(transactionId);
	}

	public List<TransactionDTO> findAll() {
		return transactionRepository.findAll().stream().map(TransactionDTO::of).collect(Collectors.toList());
	}

	public List<TransactionDTO> findAllByUserId(Long userId) {
		return transactionRepository.findByUserId(userId).stream().map(TransactionDTO::of).collect(Collectors.toList());
	}

	public TransactionDTO findById(Long transactionId) {
		
		Optional<Transaction> entity = transactionRepository.findById(transactionId);
		
		if(entity.isPresent()) {
			return TransactionDTO.of(entity.get());
		}
		
		return null;
	}

	public List<TransactionDTO> findByParam(TransactionParam param) {
		return transactionRepository.findByParam(param).stream().map(TransactionDTO::of).collect(Collectors.toList());
	}
	
	/**
	 * Retorna os resumos de transacao de um usuario para o mes passado como parametro.
	 * 
	 * @param userId
	 * @param month
	 * @return
	 */
	public UserTransactionSummaryDTO getUserTransactionSummaryByMonth(Long userId, LocalDate date) {
		
		LocalDate dateFrom = DateUtils.getLeastDateOfMonth(date);
		LocalDate dateTo = DateUtils.getMaxDateOfMonth(date);
		
		BigDecimal overallValue = transactionRepository.getOverallValue(userId, dateTo);
		BigDecimal monthlyValue = transactionRepository.getValueByPeriod(userId, dateTo, dateFrom);
		
		TransactionParam param = new TransactionParam();
		param.setUserId(userId);
		param.setStartDate(dateFrom);
		param.setFinalDate(dateTo);
		
		List<Transaction> list = transactionRepository.findByParam(param);
		
		List<TransactionDTO> transactionDTOList = new ArrayList<>();
		
		if(list!=null) {
			
			transactionDTOList = list.stream().map(TransactionDTO::of).collect(Collectors.toList());
			
			/*
			 * Ordena a lista seguindo os criterios:
			 * 
			 * 1 - Data mais velho primeiro
			 * 2 - id, o maior id primeiro (cadastrado por ultimo)
			 */
			transactionDTOList.sort( (transaction1, transaction2) -> {
				
				if(transaction1.getDate().compareTo(transaction2.getDate()) != 0) {
					return transaction2.getDate().compareTo(transaction1.getDate());
				
				}else if(transaction1.getId().compareTo(transaction2.getId()) != 0) {
					return transaction2.getId().compareTo(transaction1.getId());
				}
				
				return 0;
			});
		}
		
		UserTransactionSummaryDTO dto = new UserTransactionSummaryDTO();
		dto.setMonthlyValue(monthlyValue);
		dto.setOverallValue(overallValue);
		dto.setTransactionList(transactionDTOList);
		
		return dto;
	}
	
	/**
	 * Retorna os resumos de transacao de um usuario para o mes passado como parametro.
	 * 
	 * @param userId
	 * @param month
	 * @return
	 */
	public HomeDashboardDTO getHomeDashboardUserByMonth(Long userId, LocalDate date) {
		
		LocalDate dateFrom = DateUtils.getLeastDateOfMonth(date);
		LocalDate dateTo = DateUtils.getMaxDateOfMonth(date);
		
		//Busca o valor total ate a data
		BigDecimal overallValue = transactionRepository.getOverallValue(userId, dateTo);
		
		//Busca o valor total do mes
		BigDecimal monthlyValue = transactionRepository.getValueByPeriod(userId, dateTo, dateFrom);
		
		TransactionParam param = new TransactionParam();
		param.setUserId(userId);
		param.setStartDate(dateFrom);
		param.setFinalDate(dateTo);
		param.setType(TransactionTypeEnum.OUT.name());

		//Busca as transaction de saida
		List<Transaction> outgoingTransactionList = transactionRepository.findByParam(param);
		
		//Busca as transactions de entrada
		param.setType(TransactionTypeEnum.IN.name());
		List<Transaction> incomeTransactionList = transactionRepository.findByParam(param);
		
		Map<String, BigDecimal> valuesByTransactionCategory = new HashMap<>();
		
		BigDecimal outgoingValue = BigDecimal.ZERO;
		if(outgoingTransactionList!=null) {
			
			// Agrupa as transacoes de saida em categorias
			for (Transaction transaction : outgoingTransactionList) {
				
				BigDecimal sum = valuesByTransactionCategory.get(transaction.getCategory().getName());
				if(sum==null){
					sum = BigDecimal.ZERO;
				}
				
				sum = sum.add(transaction.getValue());
				valuesByTransactionCategory.put(transaction.getCategory().getName(), sum);
			}
			
			for(Transaction transaction: outgoingTransactionList)
				outgoingValue = outgoingValue.add(transaction.getValue());
		}
		
		BigDecimal incomeValue = BigDecimal.ZERO;
		if(incomeTransactionList!=null) {
			
			for(Transaction transaction: incomeTransactionList)
				incomeValue = incomeValue.add(transaction.getValue());
		}
		
		List<TransactionValueByCategoryDTO> transactions = new ArrayList<>();
				
		valuesByTransactionCategory.forEach( (key, value) -> {
			
			TransactionValueByCategoryDTO dto = new TransactionValueByCategoryDTO();
			dto.setCategoryName(key);
			dto.setValue(value);
			
			transactions.add(dto);
		});
		
		
		HomeDashboardDTO dto = new HomeDashboardDTO();
		dto.setOverallValue(overallValue);
		dto.setMonthlyValue(monthlyValue);
		dto.setIncomeValue(incomeValue);
		dto.setOutgoingValue(outgoingValue);
		dto.setTransactions(transactions);
		
		return dto;
	}

	public TransactionDTO save(TransactionDTO dto) {
		
		Transaction entity = new Transaction();
		
		if(dto.getId()!=null) {
			Optional<Transaction> option = transactionRepository.findById(dto.getId());
			
			if(!option.isPresent()) {
				return null;
			}
			
			entity = option.get();
		}
		
		entity.setDescription(dto.getDescription());
		entity.setValue(dto.getValue());
		entity.setDate(dto.getDate());
		entity.setType(TransactionTypeEnum.valueOf(dto.getType()));
		entity.setUser(new User(dto.getUserId()));
		
		if(dto.getCategoryId()!=null) {
			entity.setCategory(new TransactionCategory(dto.getCategoryId()));
		}
		
		return TransactionDTO.of(transactionRepository.save(entity));
	}
	
}

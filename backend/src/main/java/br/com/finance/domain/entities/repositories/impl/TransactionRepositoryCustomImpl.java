package br.com.finance.domain.entities.repositories.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.util.StringUtils;

import br.com.finance.api.param.TransactionParam;
import br.com.finance.domain.entities.Transaction;
import br.com.finance.domain.entities.Transaction.TransactionTypeEnum;
import br.com.finance.domain.entities.TransactionCategory;
import br.com.finance.domain.entities.User;
import br.com.finance.domain.entities.repositories.TransactionRepositoryCustom;
import br.com.finance.utils.MathUtils;

public class TransactionRepositoryCustomImpl implements TransactionRepositoryCustom {
	
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Transaction> findByParam(TransactionParam param) {
    	
    	CriteriaBuilder builder = entityManager.getCriteriaBuilder();
    	CriteriaQuery<Transaction> criteria = builder.createQuery(Transaction.class);
    	Root<Transaction> transactionRoot = criteria.from(Transaction.class);
    	
    	List<Predicate> restrictionList = new ArrayList<>();
    	
    	if(param.getId()!=null) {
    		restrictionList.add(builder.equal(transactionRoot.get("id"), param.getId()));
    	}else {
    		
    		if(param.getUserId()!=null) {
    			restrictionList.add(builder.equal(transactionRoot.get("user"), new User(param.getUserId())));
    		}
    		if( !StringUtils.isEmpty( param.getDescription())) {
    			restrictionList.add(builder.like(builder.upper(transactionRoot.get("description")), "%" + param.getDescription().toUpperCase() +  "%" ));
    		}
    		if(param.getStartDate()!=null) {
    			restrictionList.add(builder.greaterThanOrEqualTo(transactionRoot.get("date"), param.getStartDate()));
    		}
    		if(param.getFinalDate()!=null) {
    			restrictionList.add(builder.lessThanOrEqualTo(transactionRoot.get("date"), param.getFinalDate()));
    		}
    		if(param.getCategoryId()!=null) {
    			restrictionList.add(builder.equal(transactionRoot.get("category"), new TransactionCategory(param.getCategoryId())));
    		}
    		if(!StringUtils.isEmpty(param.getType()) && TransactionTypeEnum.getByName(param.getType())!=null) {
    			restrictionList.add(builder.equal(transactionRoot.get("type"), TransactionTypeEnum.getByName(param.getType())));
    		}
    	}
    	
    	criteria.select(transactionRoot)
        	.where(builder.and(restrictionList.toArray(new Predicate[restrictionList.size()])));
    	
    	return entityManager.createQuery(criteria).getResultList();
    }
    
    /**
     * Retorna o valor total das transacoes do Usuario.
     * 
     * @param userId
     * @return
     */
    @Override
    public BigDecimal getOverallValue(Long userId, LocalDate dateTo){
    	
    	BigDecimal value = BigDecimal.ZERO;
    	
    	try {	
    	
    		StringBuilder sql = new StringBuilder()
    		.append(" SELECT sum(tran_nm_value)")
    		.append(" FROM transaction")
    		.append(" WHERE usac_cd_id = :paramUserId")
    		.append("   AND tran_tx_type = :paramType")
    		.append("   AND tran_dt_date < :paramDate");
    		
    		Query queryIN = entityManager.createNativeQuery(sql.toString());
    		queryIN.setParameter("paramUserId", userId);
    		queryIN.setParameter("paramType", TransactionTypeEnum.IN.name());
    		queryIN.setParameter("paramDate", dateTo);
    		
    		BigDecimal valueIN = (BigDecimal) queryIN.getSingleResult();
    		
    		Query queryOUT = entityManager.createNativeQuery(sql.toString());
    		queryOUT.setParameter("paramUserId", userId);
    		queryOUT.setParameter("paramType", TransactionTypeEnum.OUT.name());
    		queryOUT.setParameter("paramDate", dateTo);
    		
    		BigDecimal valueOut = (BigDecimal) queryOUT.getSingleResult();
        	
    		value = MathUtils.subtract(valueIN, valueOut);
        	
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return value;
    }
    
    /**
     * Retorna o valor total das transacoes do usuario do periodo.
     * 
     * @param userId
     * @param dateTo
     * @param dateFrom
     * @return
     */
    @Override
    public BigDecimal getValueByPeriod(Long userId, LocalDate dateTo, LocalDate dateFrom){
    	
    	BigDecimal value = BigDecimal.ZERO;
    	
    	if(dateTo==null || userId==null || dateFrom==null) {
    		return value;
    	}
    	
    	try {	
    		
    		StringBuilder sql = new StringBuilder()
    				.append(" SELECT sum(tran_nm_value)")
    				.append(" FROM transaction")
    				.append(" WHERE usac_cd_id = :paramUserId")
    				.append("   AND tran_tx_type = :paramType")
    				.append("   AND tran_dt_date BETWEEN :paramDateFrom AND :paramDateTo");
    		
    		Query queryIN = entityManager.createNativeQuery(sql.toString());
    		queryIN.setParameter("paramUserId", userId);
    		queryIN.setParameter("paramType", TransactionTypeEnum.IN.name());
    		queryIN.setParameter("paramDateFrom", dateFrom);
    		queryIN.setParameter("paramDateTo", dateTo);
    		
    		BigDecimal valueIN = (BigDecimal) queryIN.getSingleResult();
    		
    		Query queryOUT = entityManager.createNativeQuery(sql.toString());
    		queryOUT.setParameter("paramUserId", userId);
    		queryOUT.setParameter("paramType", TransactionTypeEnum.OUT.name());
    		queryOUT.setParameter("paramDateFrom", dateFrom);
    		queryOUT.setParameter("paramDateTo", dateTo);
    		
    		BigDecimal valueOut = (BigDecimal) queryOUT.getSingleResult();
    		
    		value = MathUtils.subtract(valueIN, valueOut);
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	return value;
    }
}

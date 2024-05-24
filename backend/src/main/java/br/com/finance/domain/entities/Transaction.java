package br.com.finance.domain.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 * 
 * @author Thiago Guimaraes
 * @date 04-05-2024
 */
@Entity(name = "transaction")
public class Transaction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transaction")
	@SequenceGenerator(name =  "seq_transaction", allocationSize = 1)
	@Column(name = "tran_cd_id")
	private Long id;
	
	@Column(name = "tran_tx_description")
	private String description;
	
	@Column(name = "tran_nm_value")
	private BigDecimal value;
	
	@Column(name = "tran_dt_date")
	private LocalDate date;
	
	@Column(name = "tran_tx_type")
	@Enumerated(EnumType.STRING)
	private TransactionTypeEnum type;
	
	@ManyToOne
	@JoinColumn(name = "usac_cd_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "trca_cd_id")
	private TransactionCategory category;
	
	public enum TransactionTypeEnum {
		IN,
		OUT;

		public static TransactionTypeEnum getByName(String type) {
			
			for(TransactionTypeEnum enum_ : TransactionTypeEnum.values()) {
				if(enum_.name().equals(type)) {
					return enum_;
				}
			}
			
			return null;
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public TransactionTypeEnum getType() {
		return type;
	}

	public void setType(TransactionTypeEnum type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public TransactionCategory getCategory() {
		return category;
	}

	public void setCategory(TransactionCategory category) {
		this.category = category;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

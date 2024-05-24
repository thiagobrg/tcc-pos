package br.com.finance.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Entity(name = "transaction_category")
public class TransactionCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_transaction_category")
	@SequenceGenerator(name = "seq_transaction_category", allocationSize = 1)
	@Column( name = "trca_cd_id")
	private Long id;
	
	@Column(name = "trca_tx_name")
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "usac_cd_id")
	private User user;

	public TransactionCategory() {}
	
	public TransactionCategory(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
		TransactionCategory other = (TransactionCategory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id)) {
			return false;
		}
		
		return true;
	}
}

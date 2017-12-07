/**
 * 
 */
package br.valinorti.posystem.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

/**
 * @author leafar
 *
 */
@Entity(name="t_pf")
@PrimaryKeyJoinColumn(name="id_customer")
public class PFCustomer extends Customer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1541797716230550445L;

	@Column(name="cpf",nullable=false,length=14)
	private String cpf;
	
	@Column(name="rg",nullable=true)
	private Long rg;

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @return the rg
	 */
	public Long getRg() {
		return rg;
	}

	/**
	 * @param cpf the cpf to set
	 */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/**
	 * @param rg the rg to set
	 */
	public void setRg(Long rg) {
		this.rg = rg;
	}
}

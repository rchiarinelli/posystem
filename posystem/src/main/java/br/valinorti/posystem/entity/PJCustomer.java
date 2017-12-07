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
@Entity(name="t_pj")
@PrimaryKeyJoinColumn(name="id_customer")
public class PJCustomer extends Customer {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5514879621621285962L;

	@Column(name="cnpj_cgc",nullable=false, length=18)
	private String cnpjCgc;
	
	@Column(name="inscr_estadual",nullable=true)
	private Long inscrEstadual;
	
	@Column(name="inscr_municipal",nullable=true)
	private Long inscrMunicipal;

	/**
	 * @return the cnpjCgc
	 */
	public String getCnpjCgc() {
		return cnpjCgc;
	}

	/**
	 * @return the inscrEstadual
	 */
	public Long getInscrEstadual() {
		return inscrEstadual;
	}

	/**
	 * @return the inscrMunicipal
	 */
	public Long getInscrMunicipal() {
		return inscrMunicipal;
	}

	/**
	 * @param cnpjCgc the cnpjCgc to set
	 */
	public void setCnpjCgc(String cnpjCgc) {
		this.cnpjCgc = cnpjCgc;
	}

	/**
	 * @param inscrEstadual the inscrEstadual to set
	 */
	public void setInscrEstadual(Long inscrEstadual) {
		this.inscrEstadual = inscrEstadual;
	}

	/**
	 * @param inscrMunicipal the inscrMunicipal to set
	 */
	public void setInscrMunicipal(Long inscrMunicipal) {
		this.inscrMunicipal = inscrMunicipal;
	}
}

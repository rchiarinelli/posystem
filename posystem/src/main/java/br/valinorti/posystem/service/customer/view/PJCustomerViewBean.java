/**
 * 
 */
package br.valinorti.posystem.service.customer.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author leafar
 *
 */
@XmlRootElement(name="pjCustomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class PJCustomerViewBean extends CustomerViewBean {

	@XmlElement
	private String cnpjCgc;
	
	@XmlElement
	private String inscrEstadual;
	
	@XmlElement
	private String inscrMunicipal;

	/**
	 * @return the cnpjCgc
	 */
	public String getCnpjCgc() {
		return cnpjCgc;
	}

	/**
	 * @param cnpjCgc the cnpjCgc to set
	 */
	public void setCnpjCgc(String cnpjCgc) {
		this.cnpjCgc = cnpjCgc;
	}

	/**
	 * @return the inscrEstadual
	 */
	public String getInscrEstadual() {
		return inscrEstadual;
	}

	/**
	 * @param inscrEstadual the inscrEstadual to set
	 */
	public void setInscrEstadual(String inscrEstadual) {
		this.inscrEstadual = inscrEstadual;
	}

	/**
	 * @return the inscrMunicipal
	 */
	public String getInscrMunicipal() {
		return inscrMunicipal;
	}

	/**
	 * @param inscrMunicipal the inscrMunicipal to set
	 */
	public void setInscrMunicipal(String inscrMunicipal) {
		this.inscrMunicipal = inscrMunicipal;
	}
}

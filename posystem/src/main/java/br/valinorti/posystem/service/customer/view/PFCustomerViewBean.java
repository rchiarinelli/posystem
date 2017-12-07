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
@XmlRootElement(name="pfCustomer")
@XmlAccessorType(XmlAccessType.FIELD)
public class PFCustomerViewBean extends CustomerViewBean {

	@XmlElement
	private String cpf;
	
	@XmlElement
	private String rg;
	
	/**
	 * 
	 */
	public PFCustomerViewBean() {
		super();
		super.setType(CUSTOMER_PF);
	}

	/**
	 * @return the cpf
	 */
	public String getCpf() {
		return cpf;
	}

	/**
	 * @return the rg
	 */
	public String getRg() {
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
	public void setRg(String rg) {
		this.rg = rg;
	}
}

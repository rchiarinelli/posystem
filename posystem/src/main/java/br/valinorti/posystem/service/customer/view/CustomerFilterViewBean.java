/**
 * 
 */
package br.valinorti.posystem.service.customer.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
@BadgerFish
@XmlRootElement(name="customerFilter")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerFilterViewBean implements ViewBean{
	
	@XmlElement
	private Integer customerId;
	@XmlElement
	private String customerName;
	@XmlElement
	private String cpf_cnpj;
	@XmlElement
	private String details;
	
	/**
	 * @return the customerId
	 */
	public Integer getCustomerId() {
		return customerId;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	/**
	 * @return the cpf_cnpj
	 */
	public String getCpf_cnpj() {
		return cpf_cnpj;
	}

	/**
	 * @param cpfCnpj the cpf_cnpj to set
	 */
	public void setCpf_cnpj(String cpfCnpj) {
		cpf_cnpj = cpfCnpj;
	}

	/**
	 * @return the details
	 */
	public String getDetails() {
		return details;
	}

	/**
	 * @param details the details to set
	 */
	public void setDetails(String details) {
		this.details = details;
	}
	
	
}

/**
 * 
 */
package br.valinorti.posystem.service.customer.view;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.validator.customer.CustomerDocument;
import br.valinorti.posystem.service.view.validator.customer.CustomerDocumentOnDB;
import br.valinorti.posystem.service.view.validator.customer.CustomerDocumentType;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="addPJCustomer")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AddPJCustomerViewBean extends PJCustomerViewBean {
	
	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#getCustomerName()
	 */
	@Override
	@XmlElement
	@NotNull(message="O nome deve ser informado.")
	public String getName() {
		return super.getName();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setCustomerName(java.lang.String)
	 */
	@Override
	public void setName(String customerName) {
		super.setName(customerName);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PJCustomerViewBean#getCnpjCgc()
	 */
	@Override
	@XmlElement
	@NotNull(message="O CNPJ/CGC deve ser informado.")
	@CustomerDocument(value=CustomerDocumentType.CNPJ,message="O CNPJ/CGC informado &eacute; inv&aacute;lido.")
	@CustomerDocumentOnDB(message="O CNPJ/CGC informado j&aacute; existe no sistema.",field="cnpjCgc")
	public String getCnpjCgc() {
			return super.getCnpjCgc();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PJCustomerViewBean#setCnpjCgc(java.lang.String)
	 */
	@Override
	public void setCnpjCgc(String cnpjCgc) {
		super.setCnpjCgc(cnpjCgc);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PJCustomerViewBean#getInscrEstadual()
	 */
	@XmlElement
	@Override
	public String getInscrEstadual() {
		return super.getInscrEstadual();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PJCustomerViewBean#setInscrEstadual(java.lang.String)
	 */
	@Override
	public void setInscrEstadual(String inscrEstadual) {
		super.setInscrEstadual(inscrEstadual);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PJCustomerViewBean#getInscrMunicipal()
	 */
	@Override
	@XmlElement
	public String getInscrMunicipal() {
		return super.getInscrMunicipal();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PJCustomerViewBean#setInscrMunicipal(java.lang.String)
	 */
	@Override
	public void setInscrMunicipal(String inscrMunicipal) {
		super.setInscrMunicipal(inscrMunicipal);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#getId()
	 */
	@Override
	@XmlElement
	public Integer getId() {
		return super.getId();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setId(java.lang.Integer)
	 */
	@Override
	public void setId(Integer customerId) {
		super.setId(customerId);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#getDetails()
	 */
	@Override
	@XmlElement
	public String getDetails() {
		return super.getDetails();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setDetails(java.lang.String)
	 */
	@Override
	public void setDetails(String details) {
		super.setDetails(details);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#getAddresses()
	 */
	@Override
	@XmlElement
	public CustomerAddressView[] getAddresses() {
		return super.getAddresses();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#getContacts()
	 */
	@Override
	@XmlElement
	public CustomerContactView[] getContacts() {
		return super.getContacts();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setAddresses(br.valinorti.posystem.service.customer.view.CustomerAddressView[])
	 */
	@Override
	public void setAddresses(CustomerAddressView[] addresses) {
		super.setAddresses(addresses);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setContacts(br.valinorti.posystem.service.customer.view.CustomerContactView[])
	 */
	@Override
	public void setContacts(CustomerContactView[] contacts) {
		super.setContacts(contacts);
	}

	
	
}


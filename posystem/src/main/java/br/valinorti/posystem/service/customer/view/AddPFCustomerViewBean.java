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
@XmlRootElement(name="addPFCustomer")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class AddPFCustomerViewBean extends PFCustomerViewBean {

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PFCustomerViewBean#getCpf()
	 */
	@Override
	@CustomerDocument(value=CustomerDocumentType.CPF,message="O CPF informado &eacute; inv&aacute;lido.")
	@CustomerDocumentOnDB(message="O CPF informado j&aacute; existe no sistema.",field="cpf")
	@NotNull(message="O CPF deve ser informado.")
	@XmlElement
	public String getCpf() {
		return super.getCpf();
	}



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
	 * @see br.valinorti.posystem.service.customer.view.PFCustomerViewBean#setCpf(java.lang.String)
	 */
	@Override
	public void setCpf(String cpf) {
		super.setCpf(cpf);
	}



	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setName(java.lang.String)
	 */
	@Override
	public void setName(String customerName) {
		super.setName(customerName);
	}



	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PFCustomerViewBean#getRg()
	 */
	@Override
	@XmlElement
	public String getRg() {
		return super.getRg();
	}



	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PFCustomerViewBean#setRg(java.lang.String)
	 */
	@Override
	public void setRg(String rg) {
		super.setRg(rg);
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
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setAddresses(java.util.Set)
	 */
	@Override
	public void setAddresses(CustomerAddressView[] addresses) {
		super.setAddresses(addresses);
	}



	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#setContacts(java.util.Set)
	 */
	@Override
	public void setContacts(CustomerContactView[] contacts) {
		super.setContacts(contacts);
	}
	
	
	
	
}

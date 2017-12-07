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
@XmlRootElement(name="updatePFCustomer")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class UpdatePFCustomerViewBean extends PFCustomerViewBean {

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.PFCustomerViewBean#getRg()
	 */
	@Override
	@XmlElement
	public String getRg() {
		return super.getRg();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#getCustomerId()
	 */
	@Override
	@XmlElement
	public Integer getId() {
		return super.getId();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerViewBean#getCustomerName()
	 */
	@Override
	@XmlElement
	public String getName() {
		return super.getName();
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
}

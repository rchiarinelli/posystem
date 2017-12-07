/**
 * 
 */
package br.valinorti.posystem.service.customer.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="customer")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerViewBean implements ViewBean{
	/**
	 * Campo que define o tipo do cliente. PJ.
	 */
	public static final String CUSTOMER_PJ = "pj";
	/**
	 * Campo que define o tipo do cliente. PF.
	 */
	public static final String CUSTOMER_PF = "pf";
	
	@XmlElement
	private Integer id;
	@XmlElement
	private String name;
	@XmlElement
	private String details;
	@XmlElement
	private CustomerAddressView[] addresses;
	@XmlElement
	private CustomerContactView[] contacts;
	@XmlElement
	private String type;
	
	/**
	 * @return the customerId
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setId(Integer customerId) {
		this.id = customerId;
	}

	/**
	 * @return the customerName
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setName(String customerName) {
		this.name = customerName;
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

	/**
	 * @return the addresses
	 */
	public CustomerAddressView[] getAddresses() {
		return addresses;
	}

	/**
	 * @return the contacts
	 */
	public CustomerContactView[] getContacts() {
		return contacts;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(CustomerAddressView[] addresses) {
		this.addresses = addresses;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(CustomerContactView[] contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
}

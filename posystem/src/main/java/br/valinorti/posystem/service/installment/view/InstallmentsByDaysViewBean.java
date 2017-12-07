/**
 * 
 */
package br.valinorti.posystem.service.installment.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author rchiari
 *
 */
@XmlRootElement(name="installmentsByDay")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstallmentsByDaysViewBean implements ViewBean {
	@XmlElement	
	private String id;
	@XmlElement
	private String date;
	@XmlElement
	private String customerName;
	@XmlElement
	private String value;

	/**
	 * 
	 */
	public InstallmentsByDaysViewBean() {
		super();
	}

	
	
	/**
	 * @param date
	 * @param customerName
	 * @param value
	 */
	public InstallmentsByDaysViewBean(String date, String customerName,
			String value) {
		super();
		this.date = date;
		this.customerName = customerName;
		this.value = value;
	}
	

	/**
	 * @param id
	 * @param date
	 * @param customerName
	 * @param value
	 */
	public InstallmentsByDaysViewBean(String id, String date,
			String customerName, String value) {
		super();
		this.id = id;
		this.date = date;
		this.customerName = customerName;
		this.value = value;
	}



	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}



	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}



	/**
	 * @return the customerName
	 */
	public String getCustomerName() {
		return customerName;
	}



	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}



	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}



	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}



	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}



	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	
}

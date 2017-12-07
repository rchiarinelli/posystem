/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * Classe usada para armazenar os dados para filtro de dados.
 * 
 * 
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="requestFilter")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestFilterViewBean implements ViewBean{
	
	@XmlElement
	private String customer;
	@XmlElement
	private String orderCodes;
	@XmlElement
	private String description;
	@XmlElement
	private String[] openDateRange;
	@XmlElement
	private String[] deliverDateRange;
	@XmlElement
	private String status;

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @return the poCodes
	 */
	public String getOrderCodes() {
		return orderCodes;
	}

	/**
	 * @param poCodes the poCodes to set
	 */
	public void setOrderCodes(String poCodes) {
		this.orderCodes = poCodes;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the openDateRange
	 */
	public String[] getOpenDateRange() {
		return openDateRange;
	}

	/**
	 * @param openDateRange the openDateRange to set
	 */
	public void setOpenDateRange(String[] openDateRange) {
		this.openDateRange = openDateRange;
	}

	/**
	 * @return the deliverDateRange
	 */
	public String[] getDeliverDateRange() {
		return deliverDateRange;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param deliverDateRange the deliverDateRange to set
	 */
	public void setDeliverDateRange(String[] deliverDateRange) {
		this.deliverDateRange = deliverDateRange;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

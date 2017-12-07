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
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="request_add")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestViewBean implements ViewBean{

	@XmlElement
	private String status;

	@XmlElement
	private String customer;
	
	@XmlElement
	private String description;
	
	@XmlElement
	private String price;
	
	@XmlElement
	private String qtd;
	
	@XmlElement
	private String code;
	
	@XmlElement
	private String requestId;
	
	@XmlElement
	private String openDate;

	@XmlElement
	private String deliverDate;
	
	/**
	 * @return the status
	 */
	
	public String getStatus() {
		return status;
	}

	/**
	 * @return the customer
	 */
	public String getCustomer() {
		return customer;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @return the qtd
	 */
	public String getQtd() {
		return qtd;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer) {
		this.customer = customer;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @param qtd the qtd to set
	 */
	public void setQtd(String qtd) {
		this.qtd = qtd;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the requestId
	 */
	public String getRequestId() {
		return requestId;
	}

	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	/**
	 * @return the openDate
	 */
	public String getOpenDate() {
		return openDate;
	}

	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	/**
	 * @return the deliverDate
	 */
	public String getDeliverDate() {
		return deliverDate;
	}

	/**
	 * @param deliverDate the deliverDate to set
	 */
	public void setDeliverDate(String deliverDate) {
		this.deliverDate = deliverDate;
	}
	
}

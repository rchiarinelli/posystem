/**
 * 
 */
package br.valinorti.posystem.service.billing.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author leafar
 *
 */
@XmlRootElement(name="billingFilter")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillingFilterViewBean  {

	@XmlElement
	private String customerId;
	
	@XmlElement
	private String[] billedDateRange;

	@XmlElement
	private String status;

	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}

	/**
	 * @return the billedDateRange
	 */
	public String[] getBilledDateRange() {
		return billedDateRange;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	/**
	 * @param billedDateRange the billedDateRange to set
	 */
	public void setBilledDateRange(String[] billedDateRange) {
		this.billedDateRange = billedDateRange;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}

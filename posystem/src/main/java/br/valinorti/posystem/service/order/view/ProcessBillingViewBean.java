/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author leafar
 *
 */
@XmlRootElement(name="processBillingBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessBillingViewBean implements ViewBean {
	@XmlElement
	private String orderId;
	@XmlElement
	private String paymentType;
	@XmlElement
	private List<ProcessBillingValuesViewBean> values;

	/**
	 * @return the requestId
	 */
	public String getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the requestId to set
	 */
	public void setRequestId(String orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the paymentType
	 */
	public String getPaymentType() {
		return paymentType;
	}

	/**
	 * @param paymentType the paymentType to set
	 */
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	/**
	 * @return the values
	 */
	public List<ProcessBillingValuesViewBean> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(List<ProcessBillingValuesViewBean> values) {
		this.values = values;
	}
	
	
	
}

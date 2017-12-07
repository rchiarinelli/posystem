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
@XmlRootElement(name="installmentDashboardViewBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstallmentDashboardViewBean implements ViewBean {
	@XmlElement
	private String installmentId;
	@XmlElement
	private String customerName;
	@XmlElement
	private String orderCode;
	@XmlElement
	private String orderFinalPrice;
	@XmlElement
	private String date;
	@XmlElement
	private String status;
	@XmlElement
	private String value;

	/**
	 * 
	 */
	public InstallmentDashboardViewBean() {
		super();
	}

	/**
	 * @param customerName
	 * @param orderCode
	 * @param orderFinalPrice
	 * @param date
	 * @param status
	 * @param value
	 */
	public InstallmentDashboardViewBean(String customerName, String orderCode,
			String orderFinalPrice, String date, String status, String value) {
		super();
		this.customerName = customerName;
		this.orderCode = orderCode;
		this.orderFinalPrice = orderFinalPrice;
		this.date = date;
		this.status = status;
		this.value = value;
	}

	/**
	 * @return the installmentId
	 */
	public String getInstallmentId() {
		return installmentId;
	}

	/**
	 * @param installmentId the installmentId to set
	 */
	public void setInstallmentId(String installmentId) {
		this.installmentId = installmentId;
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
	 * @return the orderCode
	 */
	public String getOrderCode() {
		return orderCode;
	}

	/**
	 * @param orderCode the orderCode to set
	 */
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	/**
	 * @return the orderFinalPrice
	 */
	public String getOrderFinalPrice() {
		return orderFinalPrice;
	}

	/**
	 * @param orderFinalPrice the orderFinalPrice to set
	 */
	public void setOrderFinalPrice(String orderFinalPrice) {
		this.orderFinalPrice = orderFinalPrice;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}

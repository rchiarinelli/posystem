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
 * @author leafar
 *
 */
@XmlRootElement(name="payInstallment")
@XmlAccessorType(XmlAccessType.FIELD)
public class PayIntallmentViewBean implements ViewBean {
	@XmlElement
	private String installmentId;
	@XmlElement
	private String paidValue;

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
	 * @return the paidValue
	 */
	public String getPaidValue() {
		return paidValue;
	}

	/**
	 * @param paidValue the paidValue to set
	 */
	public void setPaidValue(String paidValue) {
		this.paidValue = paidValue;
	}
	
	
	
}

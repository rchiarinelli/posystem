/**
 * 
 */
package br.valinorti.posystem.service.installment.view;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ServiceResponse;

/**
 * @author rchiari
 *
 */
@XmlRootElement(name="pendingInstallments")
@XmlAccessorType(XmlAccessType.FIELD)
public class RetrievePendingInstallmentsByDaysResponse extends ServiceResponse {

	@XmlElement
	private List<InstallmentsByDaysViewBean> installments;

	/**
	 * @return the installments
	 */
	public List<InstallmentsByDaysViewBean> getInstallments() {
		return installments;
	}

	/**
	 * @param installments the installments to set
	 */
	public void setInstallments(List<InstallmentsByDaysViewBean> installments) {
		this.installments = installments;
	}
	
	
	
}

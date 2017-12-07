/**
 * 
 */
package br.valinorti.posystem.service.installment.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ServiceResponse;

/**
 * @author rchiari
 *
 */
@XmlRootElement(name="installmentDashboard")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstallmentDashboardResponse extends ServiceResponse {
	@XmlElement
	private InstallmentDashboardViewBean installment;

	/**
	 * @return the installment
	 */
	public InstallmentDashboardViewBean getInstallment() {
		return installment;
	}

	/**
	 * @param installment the installment to set
	 */
	public void setInstallment(InstallmentDashboardViewBean installment) {
		this.installment = installment;
	}
}

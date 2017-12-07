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
 * @author leafar
 *
 */
@XmlRootElement(name="installmentService")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstallmentServiceResponse extends ServiceResponse {
	@XmlElement
	private InstallmentViewBean viewBean;

	/**
	 * @return the viewBean
	 */
	public InstallmentViewBean getViewBean() {
		return viewBean;
	}

	/**
	 * @param viewBean the viewBean to set
	 */
	public void setViewBean(InstallmentViewBean viewBean) {
		this.viewBean = viewBean;
	}
}

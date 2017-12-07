/**
 * 
 */
package br.valinorti.posystem.service.billing.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ServiceResponse;

/**
 * @author leafar
 *
 */
@XmlRootElement(name="billingService")
@XmlAccessorType(XmlAccessType.FIELD)
public class BillingServiceResponse extends ServiceResponse {
	@XmlElement
	private BillingViewBean viewBean;

	/**
	 * @return the viewBean
	 */
	public BillingViewBean getViewBean() {
		return viewBean;
	}

	/**
	 * @param viewBean the viewBean to set
	 */
	public void setViewBean(BillingViewBean viewBean) {
		this.viewBean = viewBean;
	}
}

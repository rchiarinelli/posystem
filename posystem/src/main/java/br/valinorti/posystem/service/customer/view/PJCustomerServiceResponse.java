/**
 * 
 */
package br.valinorti.posystem.service.customer.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ServiceResponse;

/**
 * @author leafar
 *
 */
@XmlRootElement(name="serviceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class PJCustomerServiceResponse extends ServiceResponse {

	@XmlElement
	private PJCustomerViewBean viewBean;

	/**
	 * @return the viewBean
	 */
	public PJCustomerViewBean getViewBean() {
		return viewBean;
	}

	/**
	 * @param viewBean the viewBean to set
	 */
	public void setViewBean(PJCustomerViewBean viewBean) {
		this.viewBean = viewBean;
	}
	
	
	
}

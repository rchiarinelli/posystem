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
public class PFCustomerServiceResponse extends ServiceResponse {

	@XmlElement
	private PFCustomerViewBean viewBean;
	
	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerServiceResponse#getViewBeanResponse()
	 */
	public PFCustomerViewBean getViewBean() {
		return this.viewBean;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.view.CustomerServiceResponse#setViewBeanResponse(br.valinorti.posystem.service.customer.view.CustomerViewBean)
	 */
	
	public void setViewBean(PFCustomerViewBean viewBeanResponse) {
		this.viewBean = viewBeanResponse;
	}
}

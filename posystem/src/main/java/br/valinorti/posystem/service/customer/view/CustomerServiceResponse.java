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
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="serviceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class CustomerServiceResponse extends ServiceResponse {
	
	@XmlElement
	private CustomerViewBean viewBeanResponse;

	/**
	 * @return the viewBeanResponse
	 */
	public CustomerViewBean getViewBeanResponse() {
		return viewBeanResponse;
	}

	/**
	 * @param viewBeanResponse the viewBeanResponse to set
	 */
	public void setViewBeanResponse(CustomerViewBean viewBeanResponse) {
		this.viewBeanResponse = viewBeanResponse;
	}
	
	
	
}

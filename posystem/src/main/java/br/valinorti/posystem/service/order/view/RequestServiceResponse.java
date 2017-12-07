/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="serviceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestServiceResponse extends ServiceResponse {

	@XmlElement
	private RequestViewBean viewBeanResponse;

	/**
	 * @return the viewBeanResponse
	 */
	public ViewBean getViewBeanResponse() {
		return viewBeanResponse;
	}

	/**
	 * @param viewBeanResponse the viewBeanResponse to set
	 */
	public void setViewBeanResponse(RequestViewBean viewBeanResponse) {
		this.viewBeanResponse = viewBeanResponse;
	}

	
	
}

/**
 * 
 */
package br.valinorti.posystem.service.po.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ServiceResponse;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="poServiceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class POServiceResponse extends ServiceResponse {
	@XmlElement
	private POViewBean viewBean;

	/**
	 * @return the viewBean
	 */
	public POViewBean getViewBean() {
		return viewBean;
	}

	/**
	 * @param viewBean the viewBean to set
	 */
	public void setViewBean(POViewBean viewBean) {
		this.viewBean = viewBean;
	}
	
	

}

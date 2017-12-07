/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="requestList")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestListViewBean implements ViewBean{
	@XmlElement
	private RequestViewBean[] items;

	/**
	 * @return the list
	 */
	public RequestViewBean[] getItems() {
		return items;
	}

	/**
	 * @param list the list to set
	 */
	public void setItems(RequestViewBean[] list) {
		this.items = list;
	}
	
	

}

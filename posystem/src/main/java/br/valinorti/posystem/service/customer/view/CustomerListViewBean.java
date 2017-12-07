/**
 * 
 */
package br.valinorti.posystem.service.customer.view;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
@BadgerFish
@XmlRootElement(name="customerList")
public class CustomerListViewBean implements ViewBean{
	
	private List<CustomerViewBean> viewList;

	public CustomerListViewBean() {
		super();
		this.viewList = new ArrayList<CustomerViewBean>();
	}

	public CustomerListViewBean(List<CustomerViewBean> viewList) {
		this();
		this.viewList = viewList;
	}

	/**
	 * @return the viewList
	 */
	@XmlElement(name="items")
	public List<CustomerViewBean> getViewList() {
		return viewList;
	}

	/**
	 * @param viewList the viewList to set
	 */
	public void setViewList(List<CustomerViewBean> viewList) {
		this.viewList = viewList;
	}
	
	

}

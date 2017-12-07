/**
 * 
 */
package br.valinorti.posystem.service.view;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

/**
 * @author Rafael Chiarinelli
 *
 */
@BadgerFish
@XmlRootElement(name="utility")
public class UtilityViewBean implements ViewBean {

	private String value;
	
	
	/**
	 * 
	 */
	public UtilityViewBean() {
		super();
	}
	/**
	 * 
	 * @param value
	 */
	public UtilityViewBean(String value) {
		super();
		this.value = value;
	}

	/**
	 * @return the value
	 */
	@XmlElement
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}

/**
 * 
 */
package br.valinorti.posystem.service.po.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="poCode")
@XmlAccessorType(XmlAccessType.FIELD)
public class POCode implements ViewBean {
	@XmlElement
	private String value;

	
	/**
	 * 
	 */
	public POCode() {
		super();
	}


	/**
	 * 
	 * @param value
	 */
	public POCode(String value) {
		super();
		this.value = value;
	}


	/**
	 * @return the value
	 */
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

/**
 * 
 */
package br.valinorti.posystem.service.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="keyValueBean")
@XmlAccessorType(XmlAccessType.FIELD)
public class KeyValueBean implements ViewBean {

	@XmlElement
	private String value;
	
	@XmlElement
	private String text;


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

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	
	
}

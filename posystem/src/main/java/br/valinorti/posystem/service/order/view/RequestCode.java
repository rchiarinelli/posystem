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
@XmlRootElement(name="requestCode")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestCode implements ViewBean{
	
	@XmlElement
	private String value;

	public RequestCode() {
		super();
	}
	
	public RequestCode(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

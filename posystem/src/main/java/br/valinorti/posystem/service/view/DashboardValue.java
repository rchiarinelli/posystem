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
@XmlRootElement(name="dashboardValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class DashboardValue implements ViewBean {

	@XmlElement(name="value")
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

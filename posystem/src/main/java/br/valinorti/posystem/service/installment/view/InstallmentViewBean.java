/**
 * 
 */
package br.valinorti.posystem.service.installment.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author leafar
 *
 */
@XmlRootElement(name="installment")
@XmlAccessorType(XmlAccessType.FIELD)
public class InstallmentViewBean {
	@XmlElement
	private String id; 
	@XmlElement
	private String date;
	@XmlElement
	private String value;
	@XmlElement
	private String statusIntValue;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
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
	/**
	 * @return the statusIntValue
	 */
	public String getStatusIntValue() {
		return statusIntValue;
	}
	/**
	 * @param statusIntValue the statusIntValue to set
	 */
	public void setStatusIntValue(String statusIntValue) {
		this.statusIntValue = statusIntValue;
	}
}
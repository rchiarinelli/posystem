/**
 * 
 */
package br.valinorti.posystem.service.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author rchiari
 *
 */
@XmlRootElement(name="serviceResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class ServiceResponse implements ViewBean{

	@XmlElement
	private Integer status;
	
	@XmlElement
	private String message;
	
	public ServiceResponse() {
		super();
	}

	
	
	public ServiceResponse(String message, Integer status) {
		super();
		this.message = message;
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

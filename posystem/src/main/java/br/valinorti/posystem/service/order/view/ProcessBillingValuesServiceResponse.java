/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.ServiceResponse;

/**
 * @author rchiari
 *
 */
@XmlRootElement(name="processBillingValues")
@XmlAccessorType(XmlAccessType.FIELD)
public class ProcessBillingValuesServiceResponse extends ServiceResponse {
	@XmlElement
	private String gridValue;

	public String getGridValue() {
		return gridValue;
	}

	public void setGridValue(String gridValue) {
		this.gridValue = gridValue;
	}
}

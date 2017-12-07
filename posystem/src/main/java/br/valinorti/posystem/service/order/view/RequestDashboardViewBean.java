/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.valinorti.posystem.service.view.DashboardValue;
import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="requestValues")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequestDashboardViewBean implements ViewBean {
	@XmlElement
	private DashboardValue open;
	@XmlElement
	private DashboardValue executing;
	@XmlElement
	private DashboardValue billed;
	@XmlElement
	private DashboardValue closed;

	
	
	public RequestDashboardViewBean() {
		super();
		this.open = new DashboardValue();
		this.executing = new DashboardValue();
		this.billed = new DashboardValue();
		this.closed = new DashboardValue();
	}

	public DashboardValue getOpen() {
		return open;
	}

	public void setOpen(DashboardValue open) {
		this.open = open;
	}

	public DashboardValue getExecuting() {
		return executing;
	}

	public void setExecuting(DashboardValue executing) {
		this.executing = executing;
	}

	public DashboardValue getBilled() {
		return billed;
	}

	public void setBilled(DashboardValue billed) {
		this.billed = billed;
	}

	/**
	 * @return the closed
	 */
	public DashboardValue getClosed() {
		return closed;
	}

	/**
	 * @param closed the closed to set
	 */
	public void setClosed(DashboardValue closed) {
		this.closed = closed;
	}
	
	
}

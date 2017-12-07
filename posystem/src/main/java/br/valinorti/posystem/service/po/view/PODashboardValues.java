/**
 * 
 */
package br.valinorti.posystem.service.po.view;

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
@XmlRootElement(name="poValues")
@XmlAccessorType(XmlAccessType.FIELD)
public class PODashboardValues implements ViewBean {
	
	@XmlElement
	private DashboardValue planning;
	@XmlElement
	private DashboardValue production;
	@XmlElement
	private DashboardValue concluded;
	
	public PODashboardValues() {
		super();
		this.planning = new DashboardValue();
		this.production = new DashboardValue();
		this.concluded = new DashboardValue();
	}

	/**
	 * @return the planning
	 */
	public DashboardValue getPlanning() {
		return planning;
	}

	/**
	 * @param planning the planning to set
	 */
	public void setPlanning(DashboardValue planning) {
		this.planning = planning;
	}

	/**
	 * @return the production
	 */
	public DashboardValue getProduction() {
		return production;
	}

	/**
	 * @param production the production to set
	 */
	public void setProduction(DashboardValue production) {
		this.production = production;
	}

	/**
	 * @return the conclued
	 */
	public DashboardValue getConclued() {
		return concluded;
	}

	/**
	 * @param conclued the conclued to set
	 */
	public void setConclued(DashboardValue conclued) {
		this.concluded = conclued;
	}
	
}

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
@XmlRootElement(name="poFilter")
@XmlAccessorType(XmlAccessType.FIELD)
public class POFilterViewBean implements ViewBean {
	@XmlElement
	private String codes;
	@XmlElement
	private String[] requests;
	@XmlElement
	private String[] openDateRange;
	@XmlElement
	private String status;
	/**
	 * @return the codes
	 */
	public String getCodes() {
		return codes;
	}
	/**
	 * @return the requests
	 */
	public String[] getRequests() {
		return requests;
	}
	/**
	 * @return the openDateRange
	 */
	public String[] getOpenDateRange() {
		return openDateRange;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param codes the codes to set
	 */
	public void setCodes(String codes) {
		this.codes = codes;
	}
	/**
	 * @param requests the requests to set
	 */
	public void setRequests(String[] requests) {
		this.requests = requests;
	}
	/**
	 * @param openDateRange the openDateRange to set
	 */
	public void setOpenDateRange(String[] openDateRange) {
		this.openDateRange = openDateRange;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
}

/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import java.util.Arrays;

import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author rchiari
 *
 */
public class ProcessBillingValuesViewBean implements ViewBean {

	private String value;
	
	private String date;

	private String status;
	
	private String[] compositeId;
	
	public ProcessBillingValuesViewBean() {
		super();
	}
	
	

	public ProcessBillingValuesViewBean(String value, String date) {
		super();
		this.value = value;
		this.date = date;
	}
	
	


	/**
	 * @param value
	 * @param date
	 * @param status
	 * @param compositeId
	 */
	public ProcessBillingValuesViewBean(String value, String date,
			String status, String[] compositeId) {
		super();
		this.value = value;
		this.date = date;
		this.status = status;
		this.compositeId = compositeId;
	}



	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}



	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}



	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @return the compositeId
	 */
	public String[] getCompositeId() {
		return compositeId;
	}



	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}



	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * @param compositeId the compositeId to set
	 */
	public void setCompositeId(String[] compositeId) {
		this.compositeId = compositeId;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProcessBillingValuesViewBean [value=" + value + ", date="
				+ date + ", status=" + status + ", compositeId="
				+ Arrays.toString(compositeId) + "]";
	}
}

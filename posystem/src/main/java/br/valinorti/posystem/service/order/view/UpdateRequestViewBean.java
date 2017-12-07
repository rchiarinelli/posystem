/**
 * 
 */
package br.valinorti.posystem.service.order.view;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="update_request")
public class UpdateRequestViewBean extends RequestViewBean {

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getDeliverDate()
	 */
	@XmlElement
	@NotNull(message="Data de entrega n&atilde;o pode ser vazia")
	@Override
	public String getDeliverDate() {
		return super.getDeliverDate();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getDescription()
	 */
	@XmlElement
	@NotNull(message="Descri&ccedil;&atilde;o n&atilde;o pode ser vazia")
	@Override
	public String getDescription() {
		return super.getDescription();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getPrice()
	 */
	@XmlElement
	@Override
	public String getPrice() {
		return super.getPrice();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getQtd()
	 */
	@XmlElement
	@NotNull(message="Quantidade n&atilde;o pode ser vazia")
	@Override
	public String getQtd() {
		return super.getQtd();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getRequestId()
	 */
	@XmlElement
	@NotNull(message="ID n&atilde;o pode ser vazio")
	@Override
	public String getRequestId() {
		return super.getRequestId();
	}
	
	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setDeliverDate(java.lang.String)
	 */
	@Override
	public void setDeliverDate(String deliverDate) {
		super.setDeliverDate(deliverDate);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setDescription(java.lang.String)
	 */
	@Override
	public void setDescription(String description) {
		super.setDescription(description);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setPrice(java.lang.String)
	 */
	@Override
	public void setPrice(String price) {
		super.setPrice(price);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setQtd(java.lang.String)
	 */
	@Override
	public void setQtd(String qtd) {
		super.setQtd(qtd);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setRequestId(java.lang.String)
	 */
	@Override
	public void setRequestId(String requestId) {
		super.setRequestId(requestId);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getCode()
	 */
	@XmlElement
	@Override
	public String getCode() {
		
		return super.getCode();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getCustomer()
	 */
	@XmlElement
	@Override
	public String getCustomer() {
		return super.getCustomer();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getOpenDate()
	 */
	@XmlElement
	@Override
	public String getOpenDate() {
		
		return super.getOpenDate();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#getStatus()
	 */
	@XmlElement
	@Override
	public String getStatus() {
		
		return super.getStatus();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setCode(java.lang.String)
	 */
	@Override
	public void setCode(String code) {
		super.setCode(code);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setCustomer(java.lang.String)
	 */
	@Override
	public void setCustomer(String customer) {
		super.setCustomer(customer);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setOpenDate(java.lang.String)
	 */
	@Override
	public void setOpenDate(String openDate) {
		super.setOpenDate(openDate);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.RequestViewBean#setStatus(java.lang.String)
	 */
	@Override
	public void setStatus(String status) {
		super.setStatus(status);
	}
	
}

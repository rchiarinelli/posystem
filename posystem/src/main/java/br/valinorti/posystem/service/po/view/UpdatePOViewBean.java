/**
 * 
 */
package br.valinorti.posystem.service.po.view;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Rafael Chiarinelli
 *
 */
@XmlRootElement(name="po_update")
public class UpdatePOViewBean extends POViewBean {


	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#getEstimatedTime()
	 */
	@XmlElement
	@NotNull(message="O tempo estimado deve ser informado.")	
	@Override
	public String getEstimatedTime() {
		return super.getEstimatedTime();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#getQtd()
	 */
	@XmlElement
	@NotNull(message="A quantidade &eacute; obrigat&oacute;ria.")	
	@Override
	public String getQtd() {
		return super.getQtd();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#getRequest()
	 */
	@XmlElement
	@NotNull(message="O pedido &eacute; obrigat&oacute;rio.")	
	@Override
	public String getRequest() {
		return super.getRequest();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#setEstimatedTime(java.lang.Double)
	 */
	@Override
	public void setEstimatedTime(String estimatedTime) {
		super.setEstimatedTime(estimatedTime);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#setQtd(java.lang.Integer)
	 */
	@Override
	public void setQtd(String qtd) {
		super.setQtd(qtd);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#setRequest(java.lang.String)
	 */
	@Override
	public void setRequest(String request) {
		super.setRequest(request);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#getDueDate()
	 */
	@XmlElement
	@NotNull(message="A data de entrega deve ser preenchido.")	
	@Override
	public String getDueDate() {
		return super.getDueDate();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#setDueDate(java.lang.String)
	 */
	@Override
	public void setDueDate(String dueDate) {
		super.setDueDate(dueDate);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#getPoId()
	 */
	@XmlElement
	@NotNull(message="Id da ordem de servi&ccedil;o &eacute; obrigat&oacute;ria.")	
	@Override
	public String getPoId() {
		return super.getPoId();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.po.view.POViewBean#setPoId(java.lang.String)
	 */
	@Override
	public void setPoId(String poId) {
		super.setPoId(poId);
	}
	
	
}

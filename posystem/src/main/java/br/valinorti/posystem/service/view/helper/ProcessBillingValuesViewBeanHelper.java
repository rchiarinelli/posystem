/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.text.ParseException;

import br.valinorti.exception.POSystemException;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;
import br.valinorti.posystem.service.order.view.ProcessBillingValuesViewBean;
import br.valinorti.util.POSystemUtils;

/**
 * @author leafar
 *
 */
public class ProcessBillingValuesViewBeanHelper extends BaseViewHelper<ProcessBillingValuesViewBean, Installment> {

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createViewInstance()
	 */
	@Override
	protected ProcessBillingValuesViewBean createViewInstance() {
		return new ProcessBillingValuesViewBean();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createEntityInstance()
	 */
	@Override
	protected Installment createEntityInstance() {
		return new Installment();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertViewBeanToEntity(java.lang.Object)
	 */
	@Override
	public synchronized Installment convertViewBeanToEntity(
			ProcessBillingValuesViewBean view) {
		Installment installment = this.createEntityInstance();
		try {
			installment.setDate(POSystemUtils.parseDate(view.getDate()));
		} catch (ParseException exc) {
			throw new POSystemException("Erro na formatacao da data passada na parcela.");
		}
		if (InstallmentStatus.PAID.getStatusValue().equals(view.getStatus())) {
			installment.setStatus(InstallmentStatus.PAID);
		}
		if (InstallmentStatus.PENDING.getStatusValue().equals(view.getStatus())) {
			installment.setStatus(InstallmentStatus.PENDING);
		}
		try {
			installment.setValue(POSystemUtils.getCurrencyValue(view.getValue()).doubleValue());
		} catch (ParseException e) {
			throw new POSystemException("Erro na formatacao da valor da parcela informada.");
		}
		return installment;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertEntityToViewBean(java.lang.Object)
	 */
	@Override
	public synchronized ProcessBillingValuesViewBean convertEntityToViewBean(
			Installment entity) {
		ProcessBillingValuesViewBean view = this.createViewInstance();
		
		if (entity.getId()!=null && entity.getId()!=0) {
			view.setCompositeId(new String[]{entity.getId().toString()});
		}
		
		view.setDate(POSystemUtils.formatDate(entity.getDate()));
		view.setStatus(entity.getStatus().getStatusValue());
		view.setValue(POSystemUtils.getCurrencyValueAsString(entity.getValue()));
		return view;
	}
	
	
}

/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.service.billing.view.BillingViewBean;
import br.valinorti.util.POSystemUtils;

/**
 * @author leafar
 *
 */
public class BillingViewBeanHelper extends BaseViewHelper<BillingViewBean,Billing> {

	@Override
	protected BillingViewBean createViewInstance() {
		return new BillingViewBean();
	}

	@Override
	protected Billing createEntityInstance() {
		return new Billing();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertViewBeanToEntity(java.lang.Object)
	 */
	@Override
	public synchronized Billing convertViewBeanToEntity(BillingViewBean view) {
		return super.convertViewBeanToEntity(view);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertEntityToViewBean(java.lang.Object)
	 */
	@Override
	public synchronized BillingViewBean convertEntityToViewBean(Billing entity) {
		BillingViewBean viewBean = this.createViewInstance();
		
		viewBean.setCustomerName(entity.getOrder().getCustomer().getName());
		viewBean.setDate(POSystemUtils.formatDate(entity.getDate()));
		viewBean.setId(entity.getId().toString());
		viewBean.setOrderCode(entity.getOrder().getOrderCode());
		viewBean.setOrderId(entity.getOrder().getId().toString());
		viewBean.setPaymentType(entity.getPaymentType().getTypeValue());
		viewBean.setValue(entity.getFinalValue());
		viewBean.setStatus(entity.getStatus().getStatusValue());
		
		return viewBean;
	}
}

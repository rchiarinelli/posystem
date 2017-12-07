/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.service.customer.view.CustomerContactView;

/**
 * @author leafar
 *
 */
public class CustomerContactViewHelper extends BaseViewHelper<CustomerContactView, CustomerContact> {

	@Override
	protected CustomerContactView createViewInstance() {
		return new CustomerContactView();
	}

	@Override
	protected CustomerContact createEntityInstance() {
		return new CustomerContact();
	}

}

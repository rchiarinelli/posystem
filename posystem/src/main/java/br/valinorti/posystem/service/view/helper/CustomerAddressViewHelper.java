/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;

/**
 * @author leafar
 *
 */
public class CustomerAddressViewHelper extends BaseViewHelper<CustomerAddressView, CustomerAddress> {
	/**
	 * 
	 */
	@Override
	protected CustomerAddressView createViewInstance() {
		return new CustomerAddressView();
	}
	/**
	 * 
	 */
	@Override
	protected CustomerAddress createEntityInstance() {
		return new CustomerAddress();
	}
}

/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import br.valinorti.exception.POSystemException;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.customer.view.CustomerFilterViewBean;
import br.valinorti.posystem.service.customer.view.CustomerViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
public class CustomerViewBeanHelper extends
		BaseViewHelper<CustomerViewBean, Customer> {

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createViewInstance()
	 */
	@Override
	protected CustomerViewBean createViewInstance() {
		return new CustomerViewBean();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createEntityInstance()
	 */
	@Override
	protected Customer createEntityInstance() {
		return new Customer() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 3051151045449980416L;
		};
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#generateEntityWithChanges(java.lang.Object, java.lang.Object)
	 */
	@Override
	public synchronized Customer generateEntityWithChanges(Customer srcBean,
			Customer targetBean) {
		return super.generateEntityWithChanges(srcBean, targetBean);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertViewBeanToEntity(java.lang.Object)
	 */
	@Override
	public synchronized Customer convertViewBeanToEntity(CustomerViewBean view) {
		return super.convertViewBeanToEntity(view);
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertEntityToViewBean(java.lang.Object)
	 */
	@Override
	public synchronized CustomerViewBean convertEntityToViewBean(Customer entity) {
		CustomerViewBean viewBean = this.createViewInstance();
		viewBean.setDetails(entity.getDetails());
		viewBean.setId(entity.getId());
		viewBean.setName(entity.getName());
		if (entity.getAddresses()!=null && !entity.getAddresses().isEmpty()) {
			Set<CustomerAddress> addressEntities = entity.getAddresses();
			CustomerAddressView[] addressViews = new CustomerAddressView[addressEntities.size()];
			CustomerAddressView addressView = null;
			int cont = 0;
			for (CustomerAddress custAddress : addressEntities) {
				addressView = new CustomerAddressView();
				addressView.setCity(custAddress.getCity());
				addressView.setComplement(custAddress.getComplement());
				addressView.setCountry(custAddress.getCountry());
				if (custAddress.getId()!=null) {
					addressView.setId(custAddress.getId().toString());					
				}
				if (custAddress.getNumber()!=null) {
					addressView.setNumber(custAddress.getNumber().toString());
				}
				addressView.setState(custAddress.getState());
				addressView.setStreet(custAddress.getStreet());
				addressView.setZipCode(custAddress.getZipCode());

				addressViews[cont] = addressView;
				cont++;
			}
			viewBean.setAddresses(addressViews);
		}
		if (entity.getContacts()!=null && !entity.getContacts().isEmpty()) {

			Set<CustomerContact> contactEntities = entity.getContacts();
			CustomerContactView[] contactViews = new CustomerContactView[contactEntities.size()];
			
			CustomerContactView contactView = null;
			int cont = 0;
			for (CustomerContact custContact : contactEntities) {
				
				contactView = new CustomerContactView();

				contactView.setEmail(custContact.getEmail());
				if (custContact.getId()!=null) {
					contactView.setId(custContact.getId().toString());
				}
				contactView.setMobilePhoneNumber(custContact.getMobilePhoneNumber());
				contactView.setName(custContact.getName());
				contactView.setOfficePhoneNumber(custContact.getOfficePhoneNumber());
				contactView.setStatus(String.valueOf(custContact.getStatus().ordinal()));
				
				contactViews[cont] =contactView;
				cont++;
			}
			viewBean.setContacts(contactViews);
		}
		if (entity instanceof PFCustomer) {
			viewBean.setType(CustomerViewBean.CUSTOMER_PF);
		} else if (entity instanceof PJCustomer) {
			viewBean.setType(CustomerViewBean.CUSTOMER_PJ);
		}
		
		
		return viewBean;		
	}
	
	/**
	 * 
	 * @param viewBean
	 * @return
	 */
	public synchronized Filter convertFilterViewBeanToFilter(CustomerFilterViewBean viewBean){
		Filter filter = new Filter();
		filter.setClazz(Customer.class);
		List<String> messages = new ArrayList<String>();

		//Tratar nome
		if (!StringUtils.isBlank(viewBean.getCustomerName())) {
			filter.addArgument(FilterConditions.LIKE, "name", "%" + viewBean.getCustomerName() + "%");
		}
		
		//Tratar CPF ou CNPJ
		if (!StringUtils.isBlank(viewBean.getCpf_cnpj())){
			filter.addArgument(FilterConditions.EQUALS, "cnpjCgc", viewBean.getCpf_cnpj());
			filter.addArgument(FilterConditions.EQUALS, "cpf", viewBean.getCpf_cnpj());
		}
		
		if (!messages.isEmpty()){
			throw new POSystemException(messages);
		}
		
		return filter;
	}
	
//	/**
//	 * Converte uma lista de entidades para uma lista de view beans.
//	 * 
//	 * @param entities
//	 * @return
//	 */
//	public synchronized CustomerListViewBean convertEntityListToViewList(List<Customer> entities){
//		CustomerListViewBean viewList = new CustomerListViewBean();
//		for (Customer customer : entities) {
//			viewList.getViewList().add(this.convertEntityToViewBean(customer));
//		}
//		
//		return viewList;
//	}	
}

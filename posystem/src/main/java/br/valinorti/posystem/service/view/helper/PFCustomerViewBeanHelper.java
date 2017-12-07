/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import br.valinorti.exception.POSystemException;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.entity.ContactStatus;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.customer.view.CustomerFilterViewBean;
import br.valinorti.posystem.service.customer.view.PFCustomerViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
public class PFCustomerViewBeanHelper extends BaseViewHelper<PFCustomerViewBean, PFCustomer> {
	
//	/**
//	 * Converte uma lista de entidades para uma lista de view beans.
//	 * 
//	 * @param entities
//	 * @return
//	 */
//	@Override
//	public synchronized CustomerListViewBean convertEntityListToViewList(List<Customer> entities){
//		CustomerListViewBean viewList = new CustomerListViewBean();
//		for (Customer customer : entities) {
//			viewList.getViewList().add(this.convertEntityToViewBean((PFCustomer)customer));
//		}
//		
//		return viewList;
//	}
	
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
			filter.addArgument(FilterConditions.EQUALS, "cpfCnpj", viewBean.getCpf_cnpj());
		}
		
		if (!messages.isEmpty()){
			throw new POSystemException(messages);
		}
		
		return filter;
	}

	/*
	 * (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createViewInstance()
	 */
	@Override
	protected synchronized PFCustomerViewBean createViewInstance() {
		return new PFCustomerViewBean();
	}
	/*
	 * (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createEntityInstance()
	 */
	@Override
	protected synchronized PFCustomer createEntityInstance() {
		return new PFCustomer();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertViewBeanToEntity(java.lang.Object)
	 */
	@Override
	public synchronized PFCustomer convertViewBeanToEntity(PFCustomerViewBean view) {
		PFCustomer entity = new PFCustomer();
		if (StringUtils.isNotBlank(view.getCpf())) {
			entity.setCpf(view.getCpf());			
		}

		entity.setDetails(view.getDetails());
		if (view.getId()!=null && view.getId()>0) {
			entity.setId(view.getId());			
		}

		entity.setName(view.getName());
		if (StringUtils.isNotBlank(view.getRg())) {
			entity.setRg(Long.parseLong(view.getRg().replace(".", "").replace("-", "")));			
		}		

		if (view.getAddresses()!=null && view.getAddresses().length>0) {
			CustomerAddressView[] addresses = view.getAddresses();
			Set<CustomerAddress> addressSet = new HashSet<CustomerAddress>();
			CustomerAddress custAddress = null;
			for (CustomerAddressView addressView : addresses) {
				custAddress = new CustomerAddress();
				custAddress.setCity(addressView.getCity());
				custAddress.setComplement(addressView.getComplement());
				custAddress.setCountry(addressView.getCountry());
				if (StringUtils.isNotBlank(addressView.getId()) && StringUtils.isNumeric(addressView.getId())) {
					custAddress.setId(Integer.parseInt(addressView.getId()));					
				}
				if (StringUtils.isNotBlank(addressView.getNumber()) && StringUtils.isNumeric(addressView.getNumber())) {
					custAddress.setNumber(Integer.parseInt(addressView.getNumber()));
				}
				custAddress.setState(addressView.getState());
				custAddress.setStreet(addressView.getStreet());
				custAddress.setZipCode(addressView.getZipCode());
				custAddress.setCustomer(entity);
				addressSet.add(custAddress);
			}
			entity.setAddresses(addressSet);
		}
		if (view.getContacts()!=null && view.getContacts().length>0) {
			CustomerContactView[] contactsView = view.getContacts();
			Set<CustomerContact> contactsSet = new HashSet<CustomerContact>();
			CustomerContact custContact = null;
			for (CustomerContactView custContView : contactsView) {
				custContact = new CustomerContact();
				custContact.setEmail(custContView.getEmail());
				if (StringUtils.isNotBlank(custContView.getId()) 
						&& StringUtils.isNumeric(custContView.getId())) {
					custContact.setId(Integer.parseInt(custContView.getId()));
				}
				custContact.setMobilePhoneNumber(custContView.getMobilePhoneNumber());
				custContact.setName(custContView.getName());
				custContact.setOfficePhoneNumber(custContView.getOfficePhoneNumber());
				if (StringUtils.isNotBlank(custContView.getStatus()) 
						&& StringUtils.isNumeric(custContView.getStatus())) {
					ContactStatus status = ContactStatus.values()[Integer.parseInt(custContView.getStatus())];
					custContact.setStatus(status);
				}
				custContact.setCustomer(entity);
				contactsSet.add(custContact);
			}
			entity.setContacts(contactsSet);
		}
		return entity;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertEntityToViewBean(java.lang.Object)
	 */
	@Override
	public synchronized PFCustomerViewBean convertEntityToViewBean(PFCustomer entity) {
		PFCustomerViewBean viewBean = this.createViewInstance();
		if (!StringUtils.isBlank(entity.getCpf())) {
			viewBean.setCpf(entity.getCpf().toString());			
		}

		viewBean.setDetails(entity.getDetails());
		viewBean.setId(entity.getId());
		viewBean.setName(entity.getName());
		if (entity.getRg()!=null && entity.getRg()>0) {
			viewBean.setRg(entity.getRg().toString());
		}
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
		
		return viewBean;
	}
}

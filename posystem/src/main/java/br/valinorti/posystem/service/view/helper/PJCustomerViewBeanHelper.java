/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import br.valinorti.posystem.entity.ContactStatus;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.customer.view.PJCustomerViewBean;
import br.valinorti.util.POSystemUtils;

/**
 * @author leafar
 *
 */
public class PJCustomerViewBeanHelper extends
		BaseViewHelper<PJCustomerViewBean, PJCustomer> {

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createViewInstance()
	 */
	@Override
	protected synchronized PJCustomerViewBean createViewInstance() {
		return new PJCustomerViewBean();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#createEntityInstance()
	 */
	@Override
	protected synchronized PJCustomer createEntityInstance() {
		return new PJCustomer();
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertViewBeanToEntity(java.lang.Object)
	 */
	@Override
	public synchronized PJCustomer convertViewBeanToEntity(PJCustomerViewBean view) {
		PJCustomer entity = this.createEntityInstance();
		
		//addresses
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
		
		//Contacts
		if (view.getContacts()!=null && view.getContacts().length > 0){
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
		if (StringUtils.isNotBlank(view.getCnpjCgc())) {
			entity.setCnpjCgc(view.getCnpjCgc());
		}
		if (StringUtils.isNotBlank(view.getInscrEstadual())) {
			entity.setInscrEstadual(POSystemUtils.convertIdentityValueStringToLong(view.getInscrEstadual()));
		}
		if (StringUtils.isNotBlank(view.getInscrMunicipal())) {
			entity.setInscrMunicipal(POSystemUtils.convertIdentityValueStringToLong(view.getInscrMunicipal()));
		}
		entity.setId(view.getId());
		entity.setDetails(view.getDetails());
		entity.setName(view.getName());

		return entity;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.view.helper.BaseViewHelper#convertEntityToViewBean(java.lang.Object)
	 */ 
	@Override
	public synchronized PJCustomerViewBean convertEntityToViewBean(PJCustomer entity) {
		PJCustomerViewBean viewBean = new PJCustomerViewBean();
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
		if (!StringUtils.isBlank(entity.getCnpjCgc())) {
			viewBean.setCnpjCgc(entity.getCnpjCgc().toString());			
		}
		viewBean.setDetails(entity.getDetails());
		viewBean.setId(entity.getId());
		if (entity.getInscrEstadual()!=null && entity.getInscrEstadual()>0) {
			viewBean.setInscrEstadual(entity.getInscrEstadual().toString());
		}
		if (entity.getInscrMunicipal()!=null && entity.getInscrMunicipal()>0) {
			viewBean.setInscrMunicipal(entity.getInscrMunicipal().toString());
		}
		viewBean.setName(entity.getName());
		return viewBean;
	}
}

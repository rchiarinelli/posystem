/**
 * 
 */
package br.com.valinorti.posystem.test.viewhelper;

import junit.framework.Assert;

import org.junit.Test;

import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.service.customer.view.AddPFCustomerViewBean;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.customer.view.PFCustomerViewBean;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;

/**
 * @author leafar
 *
 */
public class ViewBeanHelperTest {

	private void assertCustomerEntityAndView(PFCustomer cust, PFCustomerViewBean view) {
		Assert.assertEquals(cust.getDetails(), view.getDetails());
		Assert.assertEquals(cust.getName(),view.getName());
		Assert.assertEquals(cust.getCpf(), view.getCpf());
		Assert.assertEquals(cust.getRg().longValue(),Long.parseLong(view.getRg().replace(".", "").replace("-", "")));
		
	}
	
	@Test
	public void testConvertPFCustomerToView_SimpleFields() {
		PFCustomer customer = new PFCustomer();
		customer.setCpf("22065627825L");
		customer.setDetails("This is the details");
		customer.setId(1000);
		customer.setName("NAME");
		customer.setRg(340123254L);
		customer.setStatus(CustomerStatus.ACTIVE);
		
		PFCustomerViewBean viewBean = ViewBeanHelper.getPFCustomerHelper().convertEntityToViewBean(customer);
		Assert.assertNotNull(viewBean);
		this.assertCustomerEntityAndView(customer, viewBean);
	}
	
	@Test
	public void testConvertPFCustomerViewToEntity_SimpleFields() {
		AddPFCustomerViewBean viewBean = new AddPFCustomerViewBean();
		viewBean.setCpf("11111111111");
		viewBean.setName("Rafael Chiarinelli");
		viewBean.setDetails("this is the details field");
		viewBean.setRg("111111114");
		
		PFCustomer pfCustomer = ViewBeanHelper.getPFCustomerHelper().convertViewBeanToEntity(viewBean);
		Assert.assertNotNull(pfCustomer);
		this.assertCustomerEntityAndView(pfCustomer, viewBean);
	}
	
	
	
	@Test
	public void testConvertPFCustomerViewToEntity_Address() {
		AddPFCustomerViewBean viewBean = new AddPFCustomerViewBean();
		viewBean.setCpf("11111111111");
		viewBean.setName("Rafael Chiarinelli");
		viewBean.setDetails("this is the details field");
		viewBean.setRg("111111114");
		
		//address
		CustomerAddressView address = new CustomerAddressView();
		address.setCity("Nova Odessa");
		address.setComplement("Jd. Florida");
		address.setCountry("BR");
		address.setNumber("117");
		address.setState("SP");
		address.setStreet("Rua Catarina Teixeira de Camargo");
		address.setZipCode("13460-000");
		
		CustomerAddressView[] addresses = new CustomerAddressView[1];
		addresses[0] = address;
		
		viewBean.setAddresses(addresses);		
		
		PFCustomer pfCustomer = ViewBeanHelper.getPFCustomerHelper().convertViewBeanToEntity(viewBean);
		Assert.assertNotNull(pfCustomer);
		this.assertCustomerEntityAndView(pfCustomer, viewBean);
		//Assert address
		Assert.assertFalse(pfCustomer.getAddresses().isEmpty());
	}
	
	@Test
	public void testConvertPFCustomerViewToEntity_Contact() {
		AddPFCustomerViewBean viewBean = new AddPFCustomerViewBean();
		viewBean.setCpf("11111111111");
		viewBean.setName("Rafael Chiarinelli");
		viewBean.setDetails("this is the details field");
		viewBean.setRg("111111114");
		
		//contact
		CustomerContactView contactView = new CustomerContactView();
		contactView.setEmail("johndoe@doe.com");
		contactView.setId("10");
		contactView.setMobilePhoneNumber("(99)1234-4321");
		contactView.setName("John Doe");
		contactView.setOfficePhoneNumber("(99)9999-9999");
		
		CustomerContactView[] contactsView = new CustomerContactView[1];
		contactsView[0] = contactView;
		
		viewBean.setContacts(contactsView);
		
		PFCustomer pfCustomer = ViewBeanHelper.getPFCustomerHelper().convertViewBeanToEntity(viewBean);
		Assert.assertNotNull(pfCustomer);
		this.assertCustomerEntityAndView(pfCustomer, viewBean);
		//Assert address
		Assert.assertFalse(pfCustomer.getContacts().isEmpty());
	}	
	
	
	
	
}

/**
 * 
 */
package br.com.valinorti.posystem.test.services.customer;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SavePersistentEntityCommand;
import br.valinorti.posystem.entity.ContactStatus;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;
import br.valinorti.posystem.service.customer.PJCustomerService;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.customer.view.CustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PJCustomerViewBean;
import br.valinorti.posystem.service.customer.view.UpdatePJCustomerViewBean;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;
import static org.mockito.Mockito.*;

/**
 * @author leafar
 *
 */
public class PJCustomerServiceUpdateTest extends BaseJUnitTest {

	private User user;
	/**
	 * Gera um {@link HttpServletRequest} mocked para uso dos junits
	 * 
	 * @return
	 */
	private HttpServletRequest mockRequest() {
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn(this.user);
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);
		
		return mockedRequest;
	}
	
	
	/* (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (this.user==null) {
			Subscriber subscriber = new Subscriber();
			subscriber.setCity("FAke");
			subscriber.setComplement("Fake");
			subscriber.setDocument("Fake");
			subscriber.setEmail("N<AANMAA");
			subscriber.setName("IUOIUOIASUD");
			subscriber.setNumber("IASUDOIASUDOASD");
			subscriber.setStreet("khsakdhsda");
			
			subscriber.setZipCode("IASYDIUASYDAIUSDy");
			
			HibernateExecutor<Subscriber> subscriberExec = new HibernateExecutor<Subscriber>();
			subscriberExec.executeCommand(new SavePersistentEntityCommand<Subscriber>(subscriber));
			
			this.user = new User();
			this.user.setEmail("fake@email.com");
			this.user.setLogin("loginfake");
			this.user.setPassword("12345");
			this.user.setSubscriber(subscriber);
		}
	}

	@Test
	public void testUpdateFields_NameField() {
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc("10000L");
		customer.setDetails("DETAILS");
		customer.setName("FAKE CUSTOMER");
		
		PJCustomerService service = new PJCustomerService();
		service.setRequest(this.mockRequest());
		
		
		PJCustomer result = (PJCustomer)service.addCustomer(customer);
		
		UpdatePJCustomerViewBean updateView = new UpdatePJCustomerViewBean();
		updateView.setId(result.getId());
		updateView.setDetails("NEW DETAILS");
		
		CustomerServiceResponse resp = service.updateCustomer(updateView);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getViewBeanResponse());
		
		//Assert values
		Assert.assertEquals(updateView.getDetails(), resp.getViewBeanResponse().getDetails());
	}
	
	@Test
	public void testUpdateFields_AllSimpleFields() {
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc("22222222L");
		customer.setDetails("DETAILS");
		customer.setName("FAKE CUSTOMER");
		
		PJCustomerService service = new PJCustomerService();
		service.setRequest(this.mockRequest());
		
		PJCustomer result = (PJCustomer)service.addCustomer(customer);
		
		UpdatePJCustomerViewBean updateView = new UpdatePJCustomerViewBean();
		updateView.setId(result.getId());
		updateView.setDetails("NEW DETAILS");
		updateView.setInscrEstadual("1212313123");
		updateView.setInscrMunicipal("9999999999");
		updateView.setName("New Name");
		
		
		CustomerServiceResponse resp = service.updateCustomer(updateView);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getViewBeanResponse());
		
		
		PJCustomerViewBean currentResult = (PJCustomerViewBean) resp.getViewBeanResponse();
		//Assert values
		Assert.assertEquals(updateView.getDetails(), resp.getViewBeanResponse().getDetails());
		Assert.assertEquals(updateView.getInscrEstadual(), currentResult.getInscrEstadual());
		Assert.assertEquals(updateView.getInscrMunicipal(),currentResult.getInscrMunicipal());
		Assert.assertEquals(updateView.getName(), currentResult.getName());
	}
	
	
	@Test
	public void testUpdateFields_Addresses() {
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc("333333333L");
		customer.setDetails("DETAILS");
		customer.setName("FAKE CUSTOMER");
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(new CustomerAddress(customer,"Rua X",123,"Altos do Klavin","Nova Odessa","SP","13460-000","BR"));
		addresses.add(new CustomerAddress(customer,"Rua WYZ",987,"Jd. São Jorge","Nova Odessa","SP","13460-000","BR"));
		
		customer.setAddresses(addresses);
		
		PJCustomerService service = new PJCustomerService();
		service.setRequest(this.mockRequest());
		
		PJCustomer result = (PJCustomer)service.addCustomer(customer);
		PJCustomerViewBean resultViewBean = ViewBeanHelper.getPJCustomerViewBeanHelper().convertEntityToViewBean(result);
		
		UpdatePJCustomerViewBean updateView = new UpdatePJCustomerViewBean();
		
		updateView.setId(result.getId());
		updateView.setDetails("NEW DETAILS");
		updateView.setInscrEstadual("1212313123");
		updateView.setInscrMunicipal("9999999999");
		updateView.setName("New Name");

		//Alterar dados dos enderecos
		CustomerAddressView[] addressesViewBeans = resultViewBean.getAddresses();
		CustomerAddressView[] changedAddresses = new CustomerAddressView[addressesViewBeans.length];
		
		for (int i = 0; i < addressesViewBeans.length; i++) {
			changedAddresses[i] = addressesViewBeans[i];
			changedAddresses[i].setCity("Americana");
			changedAddresses[i].setComplement("Colina");
			changedAddresses[i].setNumber("3456");
			changedAddresses[i].setStreet("Rua João XXIII");
		}
		
		updateView.setAddresses(changedAddresses);
		
		CustomerServiceResponse resp = service.updateCustomer(updateView);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getViewBeanResponse());
		
		
		PJCustomerViewBean currentResult = (PJCustomerViewBean) resp.getViewBeanResponse();
		
		//Assert nos enderecos alterados
		
		boolean found = false;
		
		for (CustomerAddressView expectedAddr : changedAddresses) {
			for (CustomerAddressView currAddr : currentResult.getAddresses()) {
				found = ((StringUtils.equals(expectedAddr.getCity(), currAddr.getCity()))
						&& (StringUtils.equals(expectedAddr.getComplement(), currAddr.getComplement()))
						&& (StringUtils.equals(expectedAddr.getCountry(), currAddr.getCountry()))
						&& (StringUtils.equals(expectedAddr.getNumber(), currAddr.getNumber()))
						&& (StringUtils.equals(expectedAddr.getState(), currAddr.getState()))
						&& (StringUtils.equals(expectedAddr.getStreet(), currAddr.getStreet()))
						&& (StringUtils.equals(expectedAddr.getZipCode(),currAddr.getZipCode())));
			}
		}
		
		Assert.assertTrue(found);
	}

	@Test
	public void testUpdateFields_Contacts() {
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc("333333333L");
		customer.setDetails("DETAILS");
		customer.setName("FAKE CUSTOMER");
		Set<CustomerContact> contacts = new HashSet<CustomerContact>();
		contacts.add(new CustomerContact("fake1","fake1@fake.com","(11)9999-9999","(12)1111-1111",ContactStatus.ACTIVE,customer));
		contacts.add(new CustomerContact("fake2","fake3@fake.com","","",ContactStatus.ACTIVE,customer));
		contacts.add(new CustomerContact("fake3","fake3@fake.com","","",ContactStatus.ACTIVE,customer));
		
		customer.setContacts(contacts);
		
		PJCustomerService service = new PJCustomerService();
		service.setRequest(this.mockRequest());
		PJCustomer result = (PJCustomer)service.addCustomer(customer);
		PJCustomerViewBean resultViewBean = ViewBeanHelper.getPJCustomerViewBeanHelper().convertEntityToViewBean(result);
		
		
		UpdatePJCustomerViewBean updateView = new UpdatePJCustomerViewBean();
		
		updateView.setId(result.getId());
		updateView.setDetails("NEW DETAILS");
		updateView.setInscrEstadual("1212313123");
		updateView.setInscrMunicipal("9999999999");
		updateView.setName("New Name");
		
		CustomerContactView[] contactViewBeans = resultViewBean.getContacts();
		CustomerContactView[] updateContacts = new CustomerContactView[contactViewBeans.length+1];
		
		for (int i = 0; i < contactViewBeans.length; i++) {
			updateContacts[i] = contactViewBeans[i];
			updateContacts[i].setStatus("0");
		}
		updateContacts[updateContacts.length-1] = new CustomerContactView();
		updateContacts[updateContacts.length-1].setEmail("fake4@fake.com");
		updateContacts[updateContacts.length-1].setMobilePhoneNumber("(19)1111-1111");
		updateContacts[updateContacts.length-1].setName("Fake4");
		updateContacts[updateContacts.length-1].setOfficePhoneNumber("(19)1111-1111");
		updateContacts[updateContacts.length-1].setStatus("1");
		
		updateView.setContacts(updateContacts);
		
		CustomerServiceResponse resp = service.updateCustomer(updateView);
		Assert.assertNotNull(resp);
		Assert.assertNotNull(resp.getViewBeanResponse());
		
	}
}

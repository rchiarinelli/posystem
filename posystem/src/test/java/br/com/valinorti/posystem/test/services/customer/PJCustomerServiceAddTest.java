/**
 * 
 */
package br.com.valinorti.posystem.test.services.customer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Assert;
import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.posystem.entity.ContactStatus;
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;
import br.valinorti.posystem.service.customer.PJCustomerService;
import br.valinorti.posystem.service.customer.view.AddPJCustomerViewBean;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.util.HibernateUtils;

/**
 * @author leafar
 *
 */
public class PJCustomerServiceAddTest extends BaseJUnitTest {
	
	@Test
	public void testAddPJCustomer_SimpleFields() {
		AddPJCustomerViewBean viewBean = new AddPJCustomerViewBean();
		viewBean.setCnpjCgc("47.684.454/0001-92");
		viewBean.setDetails("Details");
		viewBean.setInscrEstadual("9789173812");
		viewBean.setInscrMunicipal("912923123");
		viewBean.setName("Name of company");
		
		PJCustomerService service = new PJCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		
		service.setRequest(mockedRequest);
		
		ServiceResponse response = service.addCustomer(viewBean);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getMessage(),response.getStatus().toString(), "0");
	}
	
	@Test
	public void testAddPJCustomer_Addresses() {
		AddPJCustomerViewBean viewBean = new AddPJCustomerViewBean();
		viewBean.setCnpjCgc("84.258.100/0001-02");
		viewBean.setDetails("Details");
		viewBean.setInscrEstadual("9789173812");
		viewBean.setInscrMunicipal("912923123");
		viewBean.setName("Name of company");
		//Addresses
		List<CustomerAddressView> addresses = new ArrayList<CustomerAddressView>();
		CustomerAddressView address = new CustomerAddressView();
		address.setCity("Nova Odessa");
		address.setComplement("Complement");
		address.setCountry("BR");
		address.setNumber("123");
		address.setState("SP");
		address.setStreet("Street");
		address.setZipCode("687687");
		addresses.add(address);
		
		address = new CustomerAddressView();
		address.setCity("São Paulo");
		address.setComplement("Complement");
		address.setCountry("BR");
		address.setNumber("987");
		address.setState("SP");
		address.setStreet("Street");
		address.setZipCode("9999997");
		addresses.add(address);
		
		viewBean.setAddresses(addresses.toArray(new CustomerAddressView[addresses.size()]));

		PJCustomerService service = new PJCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);
		
		service.setRequest(mockedRequest);
		
		ServiceResponse response = service.addCustomer(viewBean);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getMessage(),response.getStatus().toString(), "0");
	}
	
	@Test
	public void testAddPJCustomer_Contacts() {
		AddPJCustomerViewBean viewBean = new AddPJCustomerViewBean();
		viewBean.setCnpjCgc("79.139.867/0001-82");
		viewBean.setDetails("Details");
		viewBean.setInscrEstadual("9789173812");
		viewBean.setInscrMunicipal("912923123");
		viewBean.setName("Name of company");
		//Contacts
		List<CustomerContactView> contacts = new ArrayList<CustomerContactView>();
		CustomerContactView contact = new CustomerContactView();
		contact.setEmail("johnsmit@company.com");
		contact.setMobilePhoneNumber("12167826382");
		contact.setName("John Smith");
		contact.setOfficePhoneNumber("2312321");
		contact.setStatus(String.valueOf(ContactStatus.ACTIVE.ordinal()));
		
		contacts.add(contact);
		
		contact = new CustomerContactView();
		contact.setEmail("janesmit@company.com");
		contact.setMobilePhoneNumber("12167826382");
		contact.setName("Jane Smith");
		contact.setOfficePhoneNumber("2312321");
		contact.setStatus(String.valueOf(ContactStatus.ACTIVE.ordinal()));
		
		contacts.add(contact);
		
		contact = new CustomerContactView();
		contact.setEmail("fakesmit@company.com");
		contact.setMobilePhoneNumber("12167826382");
		contact.setName("Fake Smith");
		contact.setOfficePhoneNumber("2312321");
		contact.setStatus(String.valueOf(ContactStatus.INACTIVE.ordinal()));
		
		contacts.add(contact);
		
		viewBean.setContacts(contacts.toArray(new CustomerContactView[contacts.size()]));

		PJCustomerService service = new PJCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		
		service.setRequest(mockedRequest);
		
		ServiceResponse response = service.addCustomer(viewBean);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getMessage(),response.getStatus().toString(), "0");
	}	
}
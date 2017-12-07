/**
 * 
 */
package br.com.valinorti.posystem.test.services.customer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.posystem.entity.ContactStatus;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;
import br.valinorti.posystem.service.customer.PFCustomerService;
import br.valinorti.posystem.service.customer.view.AddPFCustomerViewBean;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.customer.view.CustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PFCustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PFCustomerViewBean;
import br.valinorti.posystem.service.customer.view.UpdatePFCustomerViewBean;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.util.HibernateUtils;

/**
 * @author leafar
 *
 */
public class PFCustomerUpdateTest extends BaseJUnitTest {
	
	@Test
	public void updateCustomer_SimpleFields() throws Exception {
		AddPFCustomerViewBean customer = new AddPFCustomerViewBean();
		customer.setCpf("22065627824");
		customer.setRg("340123254");
		customer.setName("John Smith");
		customer.setDetails("This is the details field.");
		PFCustomerService customerService = new PFCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);		
		customerService.setRequest(mockedRequest);
		
		
		CustomerServiceResponse response = customerService.addCustomer(customer);
		Assert.assertNotNull(response);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), response.getStatus());
		
		//Teste do update
		UpdatePFCustomerViewBean updateViewBean = new UpdatePFCustomerViewBean();
		BeanUtils.copyProperties(updateViewBean, response.getViewBeanResponse());
		updateViewBean.setDetails("This is the new details.");
		updateViewBean.setName("Donald Duck");
		
		ServiceResponse updateResponse = customerService.updateCustomer(updateViewBean);
		Assert.assertNotNull(updateResponse);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), updateResponse.getStatus());
	}
	
	@Test
	public void updateCustomerByJSON_SimpleFields(){
		AddPFCustomerViewBean customer = new AddPFCustomerViewBean();
		customer.setCpf("652.825.548-28");
		customer.setRg("999999999");
		customer.setName("John Smith");
		customer.setDetails("This is the details field.");
		PFCustomerService customerService = new PFCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		customerService.setRequest(mockedRequest);
		
		
		CustomerServiceResponse response = customerService.addCustomer(customer);
		Assert.assertNotNull(response);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), response.getStatus());
		
		//Teste do update
		UpdatePFCustomerViewBean updateViewBean = new UpdatePFCustomerViewBean();
		updateViewBean.setName("Rafael Chiarinelli");
		updateViewBean.setRg("12.222.777-7");
		updateViewBean.setDetails("New details");
		updateViewBean.setId(response.getViewBeanResponse().getId());
		
		ServiceResponse updateResponse = customerService.updateCustomer(updateViewBean);
		Assert.assertNotNull(updateResponse);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), updateResponse.getStatus());
	}
	
	
	@Test
	public void updateCustomer_Address(){
		AddPFCustomerViewBean customer = new AddPFCustomerViewBean();
		customer.setCpf("88888888888");
		customer.setRg("222222222");
		customer.setName("John Smith");
		customer.setDetails("This is the details field.");
		PFCustomerService customerService = new PFCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		
		customerService.setRequest(mockedRequest);
		
		CustomerServiceResponse response = customerService.addCustomer(customer);
		Assert.assertNotNull(response);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), response.getStatus());
		
		//Teste do update
		UpdatePFCustomerViewBean updateViewBean = new UpdatePFCustomerViewBean();
		updateViewBean.setName("Rafael Chiarinelli");
		updateViewBean.setRg("12.222.777-7");
		updateViewBean.setDetails("New details");
		updateViewBean.setId(response.getViewBeanResponse().getId());
		
		//Adicionar um endereco
		CustomerAddressView address = new CustomerAddressView();
		address.setCity("Nova Odessa");
		address.setComplement("Jd. Florida");
		address.setCountry("BR");
		address.setNumber("117");
		address.setState("SP");
		address.setStreet("Rua Catarina Teixeira de Camargo");
		address.setZipCode("13460-000");
		
		CustomerAddressView[] addresses = new CustomerAddressView[1];
		addresses[0]=address;
		
		updateViewBean.setAddresses(addresses);		
		
		ServiceResponse updateResponse = customerService.updateCustomer(updateViewBean);
		Assert.assertNotNull(updateResponse);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), updateResponse.getStatus());
	}
	@Test
	public void updateCustomer_Contact(){
		AddPFCustomerViewBean customer = new AddPFCustomerViewBean();
		customer.setCpf("55555555555");
		customer.setRg("23123123123");
		customer.setName("John Smith");
		customer.setDetails("This is the details field.");
		PFCustomerService customerService = new PFCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		
		customerService.setRequest(mockedRequest);
		
		CustomerServiceResponse response = customerService.addCustomer(customer);
		Assert.assertNotNull(response);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), response.getStatus());
		
		//Teste do update
		UpdatePFCustomerViewBean updateViewBean = new UpdatePFCustomerViewBean();
		updateViewBean.setName("Rafael Chiarinelli");
		updateViewBean.setRg("12.222.777-7");
		updateViewBean.setDetails("New details");
		updateViewBean.setId(response.getViewBeanResponse().getId());
		
		//Associa um contato
		CustomerContactView contactView = new CustomerContactView();
		contactView.setEmail("johndoe@doe.com");
		contactView.setMobilePhoneNumber("(99)1234-4321");
		contactView.setName("John Doe");
		contactView.setOfficePhoneNumber("(99)9999-9999");
		contactView.setStatus("0");
		
		CustomerContactView[] contactsView = new CustomerContactView[1];
		contactsView[0] = contactView;
		
		updateViewBean.setContacts(contactsView);
		
		ServiceResponse updateResponse = customerService.updateCustomer(updateViewBean);
		Assert.assertNotNull(updateResponse);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), updateResponse.getStatus());
	}
	
	
	@Test
	public void updateCustomer_ExistingAddress(){
		PFCustomer customer = new PFCustomer();
		customer.setCpf("888888L");
		customer.setRg(222222222L);
		customer.setName("John Smith");
		customer.setDetails("This is the details field.");
		
		//Address
		CustomerAddress address = new CustomerAddress();
		address.setCity("Nova Odessa");
		address.setComplement("Jd. Florida");
		address.setCountry("BR");
		address.setNumber(117);
		address.setState("SP");
		address.setStreet("Rua Catarina Teixeira de Camargo");
		address.setZipCode("13460-000");
		address.setCustomer(customer);
		
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(address);
		
		customer.setAddresses(addresses);
		
		
		PFCustomerService customerService = new PFCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class,"user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		
		customerService.setRequest(mockedRequest);
		
		customerService.addCustomer(customer);

		//Recuperar cliente
		PFCustomerServiceResponse customerServiceResponse = customerService.getCustomerByID(customer.getId().toString());
		PFCustomerViewBean viewBean = (PFCustomerViewBean) customerServiceResponse.getViewBean();
		
		//UpdateView
		UpdatePFCustomerViewBean updateView = new UpdatePFCustomerViewBean();
		updateView.setAddresses(viewBean.getAddresses());
		updateView.setContacts(viewBean.getContacts());
		updateView.setCpf(viewBean.getCpf());
		updateView.setDetails(viewBean.getDetails());
		updateView.setId(viewBean.getId());
		updateView.setName(viewBean.getName());
		updateView.setRg(viewBean.getRg());
		
		
		//Alterar endereco
		for (CustomerAddressView custAddView : updateView.getAddresses()) {
			custAddView.setCity("São Paulo");
			custAddView.setComplement("Moema");
			custAddView.setNumber("323");
		}
		
		ServiceResponse updateResponse = customerService.updateCustomer(updateView);
		Assert.assertNotNull(updateResponse);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), updateResponse.getStatus());
	}
	
	@Test
	public void updateCustomer_ExistingContacts(){
		PFCustomer customer = new PFCustomer();
		customer.setCpf("8333333L");
		customer.setRg(12312324234234L);
		customer.setName("John Smith");
		customer.setDetails("This is the details field.");
		
		//Address
		CustomerContact contact = new CustomerContact();
		contact.setEmail("jsmith@smith.com");
		contact.setMobilePhoneNumber("5555-5555");
		contact.setName("John Smith");
		contact.setOfficePhoneNumber("Aasdasdsd");
		contact.setStatus(ContactStatus.ACTIVE);
		
		contact.setCustomer(customer);
		
		Set<CustomerContact> contacts = new HashSet<CustomerContact>();
		contacts.add(contact);
		
		customer.setContacts(contacts);
		
		
		PFCustomerService customerService = new PFCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		
		customerService.setRequest(mockedRequest);
		
		
		customerService.addCustomer(customer);

		//Recuperar cliente
		PFCustomerServiceResponse customerServiceResponse = customerService.getCustomerByID(customer.getId().toString());
		PFCustomerViewBean viewBean = (PFCustomerViewBean) customerServiceResponse.getViewBean();
		
		//UpdateView
		UpdatePFCustomerViewBean updateView = new UpdatePFCustomerViewBean();
		updateView.setAddresses(viewBean.getAddresses());
		updateView.setContacts(viewBean.getContacts());
		updateView.setCpf(viewBean.getCpf());
		updateView.setDetails(viewBean.getDetails());
		updateView.setId(viewBean.getId());
		updateView.setName(viewBean.getName());
		updateView.setRg(viewBean.getRg());
		
		
		//Alterar contactos
		for (CustomerContactView custContactView : updateView.getContacts()) {
			custContactView.setEmail("123-43423");
			custContactView.setStatus("0");
			custContactView.setName("Jane Smith");
		}
		
		ServiceResponse updateResponse = customerService.updateCustomer(updateView);
		Assert.assertNotNull(updateResponse);
		Assert.assertEquals(new Integer(ServiceResponseEnum.OK.ordinal()), updateResponse.getStatus());
	}	
}

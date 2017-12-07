/**
 * 
 */
package br.com.valinorti.posystem.test.services.customer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import junit.framework.Assert;

import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SavePersistentEntityCommand;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;
import br.valinorti.posystem.service.customer.PFCustomerService;
import br.valinorti.posystem.service.customer.view.AddPFCustomerViewBean;
import br.valinorti.posystem.service.customer.view.CustomerAddressView;
import br.valinorti.posystem.service.customer.view.CustomerContactView;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;

/**
 * @author leafar
 *
 */
public class PFCustomerAddTest extends BaseJUnitTest {

	private User user;
	
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
	public void testAddPFCustomerJSON_AllSimpleFields() {
		AddPFCustomerViewBean viewBean = new AddPFCustomerViewBean();
		viewBean.setCpf("99999999999");
		viewBean.setName("Rafael");
		viewBean.setDetails("this is the details field");
		viewBean.setRg("111111114");
		
		PFCustomerService customerService = new PFCustomerService();

		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn(this.user);
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);		
		customerService.setRequest(mockedRequest);
		
		ServiceResponse response = customerService.addCustomer(viewBean);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getMessage(),new Integer(ServiceResponseEnum.OK.ordinal()), response.getStatus());
	}
	
	@Test
	public void testAddPFCustomerWithAddress() {
		System.out.println("testAddPFCustomerWithAddress");
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
		
		PFCustomerService customerService = new PFCustomerService();

		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn(this.user);
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);		
		customerService.setRequest(mockedRequest);
		
		customerService.setRequest(mockedRequest);
		
		ServiceResponse response = customerService.addCustomer(viewBean);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getMessage(),new Integer(ServiceResponseEnum.OK.ordinal()), response.getStatus());
	}
	
	@Test
	public void testAddPFCustomerWithContact() {
		System.out.println("testAddPFCustomerWithContact");
		AddPFCustomerViewBean viewBean = new AddPFCustomerViewBean();
		viewBean.setCpf("33333333333");
		viewBean.setName("Rafael Chiarinelli");
		viewBean.setDetails("this is the details field");
		viewBean.setRg("331111114");
		//Contact
		CustomerContactView contactView = new CustomerContactView();
		contactView.setEmail("johndoe@doe.com");
		contactView.setMobilePhoneNumber("(99)1234-4321");
		contactView.setName("John Doe");
		contactView.setOfficePhoneNumber("(99)9999-9999");
		contactView.setStatus("0");
		
		CustomerContactView[] contactsView = new CustomerContactView[1];
		contactsView[0]=contactView;
		
		viewBean.setContacts(contactsView);
		
		PFCustomerService customerService = new PFCustomerService();

		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn(this.user);
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);		
		customerService.setRequest(mockedRequest);
		
		customerService.setRequest(mockedRequest);
		
		ServiceResponse response = customerService.addCustomer(viewBean);
		Assert.assertNotNull(response);
		Assert.assertEquals(response.getMessage(),new Integer(ServiceResponseEnum.OK.ordinal()), response.getStatus());
	}	
	
}

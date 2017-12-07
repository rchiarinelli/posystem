/**
 * 
 */
package br.com.valinorti.posystem.test.services.customer;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.filterengine.Filter;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;
import br.valinorti.posystem.service.customer.CustomerService;
import br.valinorti.posystem.service.customer.view.CustomerListViewBean;
import br.valinorti.posystem.service.customer.view.CustomerViewBean;
import br.valinorti.util.HibernateUtils;

/**
 * @author leafar
 *
 */
public class CustomerServiceFilterTest extends BaseJUnitTest {
	
	private CustomerService getMockedCustomerService() {
		CustomerService customerService = new CustomerService();
		
		User user = (User) HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1");
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn(user);
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);		
		customerService.setRequest(mockedRequest);
		return customerService;
	}
	
	@Test
	public void testFilterCustomer_CustomerByLoggedSubscriber() {
		CustomerService customerService = this.getMockedCustomerService();

		Subscriber expectedSubscriber = customerService.getAuthUser().getSubscriber();
		
		Filter filter = new Filter();
		filter.setClazz(Customer.class);
		
		List<Customer> results = customerService.listByFilter(filter);
		
		boolean areEqualsSubs = false;
		Subscriber currentSubs = null;
		
		for (Iterator<Customer> iterator = results.iterator(); iterator.hasNext();) {
			Customer customer = iterator.next();
			currentSubs = customer.getSubscriber();
			if (expectedSubscriber.equals(currentSubs)) {
				areEqualsSubs = true;
			} else {
				break;
			}
		}
		
		assertTrue("The customer filter returns values which belongs to other subscriber..: " + currentSubs, areEqualsSubs);
		
		
	}
	
	@Test
	public void testListCustomer_CustomerBySubscriber() {
		CustomerService customerService = this.getMockedCustomerService();
		CustomerListViewBean customerListViewBean = customerService.listCustomers();
		List<CustomerViewBean> results = customerListViewBean.getViewList();
		assertEquals("The result customer list has a different size than the expected ",3, results.size());
	}
	
	@Test
	public void testListCustomersWithBillings_CustomerBySubscriber() {
		CustomerService customerService = this.getMockedCustomerService();
		CustomerListViewBean customerListViewBean = customerService.listCustomersWithBillings();
		List<CustomerViewBean> results = customerListViewBean.getViewList();
		assertEquals("The customer installment quantity is different than the expected  ",2, results.size());
	}

}

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
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;
import br.valinorti.posystem.service.customer.PJCustomerService;
import br.valinorti.posystem.service.customer.view.AddPJCustomerViewBean;
import br.valinorti.posystem.service.customer.view.CustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PJCustomerServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.util.HibernateUtils;

/**
 * 
 * @author leafar
 *
 */
public class PJCustomerServiceGetTest extends BaseJUnitTest {

	@Test
	public void testGetCustomer() {
		PJCustomerService pjCustomerService = new PJCustomerService();
		
		HttpSession mockedSession = mock(HttpSession.class);
		when(mockedSession.getAttribute(POSystemSecurityInterceptor.AUTH_USER)).thenReturn((User)HibernateUtils.getSessionFactory().openSession().get(User.class, "user_loginl_1"));
		
		HttpServletRequest mockedRequest = mock(HttpServletRequest.class);
		when(mockedRequest.getSession()).thenReturn(mockedSession);	
		
		pjCustomerService.setRequest(mockedRequest);
		
		AddPJCustomerViewBean viewBean = new AddPJCustomerViewBean();
		viewBean.setCnpjCgc("14726088000138");
		viewBean.setDetails("Generic details");
		viewBean.setName("CUSTOMER" + System.currentTimeMillis());
		
		CustomerServiceResponse resp = pjCustomerService.addCustomer(viewBean);
		
		Assert.assertEquals(ServiceResponseEnum.OK, ServiceResponseEnum.values()[resp.getStatus()]);
		
		PJCustomerServiceResponse pjCustomerServiceResponse = pjCustomerService.getCustomerById((resp.getViewBeanResponse().getId().toString()));
		
		Assert.assertNotNull(pjCustomerServiceResponse);
		
		Assert.assertEquals(viewBean.getCnpjCgc(), pjCustomerServiceResponse.getViewBean().getCnpjCgc());
		Assert.assertEquals(viewBean.getDetails(), pjCustomerServiceResponse.getViewBean().getDetails());
		Assert.assertEquals(viewBean.getName(), pjCustomerServiceResponse.getViewBean().getName());
		
		
		
	}
	
}

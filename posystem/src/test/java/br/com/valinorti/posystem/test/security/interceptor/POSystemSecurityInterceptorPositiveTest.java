/**
 * 
 */
package br.com.valinorti.posystem.test.security.interceptor;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.specimpl.PathSegmentImpl;
import org.jboss.resteasy.specimpl.UriInfoImpl;
import org.jboss.resteasy.spi.HttpRequest;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SavePersistentEntityCommand;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;
import br.valinorti.posystem.service.RESTService;
import br.valinorti.posystem.service.customer.PFCustomerService;

/**
 * @author leafar
 *
 */
public class POSystemSecurityInterceptorPositiveTest extends BaseJUnitTest {
	
	private Subscriber subscriber;
	
	
	/* (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (this.subscriber==null) {
			this.subscriber = new Subscriber();
			this.subscriber.setName("BrainyIT");
			this.subscriber.setEmail("fake@brainyit.com.br");
			this.subscriber.setStreet("Rua XYZ");
			this.subscriber.setComplement("Jd Olimpia");
			this.subscriber.setNumber("4321");
			this.subscriber.setZipCode("237293874");
			this.subscriber.setCity("Nova Odessa");
			this.subscriber.setDocument("999999");

			HibernateExecutor<Subscriber> subscriberExecutor = new HibernateExecutor<Subscriber>();
			subscriberExecutor.executeCommand(new SavePersistentEntityCommand<Subscriber>(this.subscriber));
			
			User user = new User();
			user.setEmail("fake@brainyit.com.br");
			user.setLogin("fake_login");
			user.setPassword("12345");
			user.setSubscriber(this.subscriber);
			
			HibernateExecutor<User> executor = new HibernateExecutor<User>();
			executor.executeCommand(new SavePersistentEntityCommand<User>(user));
		}
	}

	@Test
	public void testPreProccess_AuthUser() throws Exception {
		POSystemSecurityInterceptor securityInterceptor = new POSystemSecurityInterceptor();
		
		//Montar o returns de cada metodo invocado no mock do SecurityContext
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getUserPrincipal()).thenReturn(new Principal() {
			@Override
			public String getName() {
				return "fake_login";
			}
		});
		
		
		//Montar o returns de cada metodo invocado no mock do HttpRequest
		HttpRequest request =  mock(HttpRequest.class);
		List<PathSegment> pathSegments = new ArrayList<PathSegment>();
		pathSegments.add(new PathSegmentImpl("subscriber"));
		pathSegments.add(new PathSegmentImpl(this.subscriber.getId().toString()));
		
		UriInfo uriInfo = new UriInfoImpl(new URI("http://localhost:8080/posystem"),new URI("/po"),"","",pathSegments);
		uriInfo.getPathParameters().putSingle("subscriber", this.subscriber.getId().toString());
		
		
		when(request.getUri()).thenReturn(uriInfo);
		
		//Montar o returns de cada metodo invocado no mock do ResourceMethod
		ResourceMethod resourceMethod = mock(ResourceMethod.class);
		when(resourceMethod.getResourceClass()).thenAnswer(new Answer<Class<? extends RESTService>>() {
			@Override
			public Class<? extends RESTService> answer(InvocationOnMock invocation)
					throws Throwable {
				PFCustomerService service = new PFCustomerService();
				return service.getClass();
			}
		});
		//Montar o mock do HttpServletRequest e o HttpSession
		HttpServletRequest servletReq = mock(HttpServletRequest.class);
		HttpSession httpSession = mock(HttpSession.class);
		when(servletReq.getSession()).thenReturn(httpSession);
		when(httpSession.getId()).thenReturn(String.valueOf(System.currentTimeMillis()));
		securityInterceptor.setServletRequest(servletReq);
		
		securityInterceptor.setSecurityContext(securityContext);
		ServerResponse response = securityInterceptor.preProcess(request, resourceMethod);
		assertNull(response);
	}
	
}

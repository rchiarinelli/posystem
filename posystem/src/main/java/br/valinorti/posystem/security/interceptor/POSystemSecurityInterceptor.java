/**
 * 
 */
package br.valinorti.posystem.security.interceptor;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.annotations.interception.Precedence;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;

import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.GetUserByLoginCommand;
import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.service.BaseRESTService;

/**
 * Interceptor usado para fazer a validacao fina das permissoes do usuario logado.
 * 
 * @author leafar
 *
 */
@Provider
@ServerInterceptor
@Precedence("SECURITY")
public class POSystemSecurityInterceptor implements PreProcessInterceptor {

	public static final String AUTH_USER = "authUser";
	
	@Context
	private SecurityContext securityContext;
	
	@Context 
	private HttpServletRequest servletRequest;
	
	/**
	 * @param securityContext the securityContext to set
	 */
	public void setSecurityContext(SecurityContext securityContext) {
		this.securityContext = securityContext;
	}

	/**
	 * @param servletRequest the servletRequest to set
	 */
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
	}




	/* (non-Javadoc)
	 * @see org.jboss.resteasy.spi.interception.PreProcessInterceptor#preProcess(org.jboss.resteasy.spi.HttpRequest, org.jboss.resteasy.core.ResourceMethod)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod resMethod)
			throws Failure, WebApplicationException {
		ServerResponse serverResponse = null;
		Class<?> methodClass = resMethod.getResourceClass();
		List<Class<?>> superClasses = (List<Class<?>>) ClassUtils.getAllSuperclasses(methodClass);
		boolean isRESTClass = false;
		for (Class<?> clazz : superClasses) {
			if (clazz.isAssignableFrom(BaseRESTService.class)) {
				isRESTClass = true;
				break;
			}
		}
		UriInfo uri = request.getUri();
		MultivaluedMap<String, String> pathParameters = uri.getPathParameters();
		String subscriber = pathParameters.getFirst("subscriber");
		Principal userPrincipal = this.securityContext.getUserPrincipal();
		
		HttpSession httpSession = this.servletRequest.getSession();		
		
		//realizar validacoes
		if ((!isRESTClass) 
			|| (StringUtils.isBlank(subscriber))
			|| (!StringUtils.isNumeric(subscriber))
			|| (userPrincipal==null)) {
			serverResponse = new ServerResponse();
			serverResponse.setStatus(Response.Status.FORBIDDEN.getStatusCode());
			return serverResponse;
		}

		
		User user = (User) httpSession.getAttribute(AUTH_USER);
		if (user==null) {
			//recuperar o usuario a partir do banco de dados
			HibernateExecutor<User> executor = new HibernateExecutor<User>();
			user = executor.executeCommand(new GetUserByLoginCommand(userPrincipal.getName()));
		}
		
		//Checar se o usuario e null
		if (user==null) {
			//Se sim, quer dizer que ele nao existe no banco de dados
			serverResponse = new ServerResponse();
			serverResponse.setStatus(Response.Status.FORBIDDEN.getStatusCode());
			return serverResponse;
		}
		
		//Verificar se o subscriber passado é igual ao subscriber do usuario recuperado
		Integer providedSubs = Integer.parseInt(subscriber);
		if (user.getSubscriber().getId().equals(providedSubs)) {
			//checar permissoes
			serverResponse = this.checkPermissions(request,resMethod);
			if (serverResponse==null) {
				//Adicionar o usuario ao Manager
				httpSession.setAttribute(AUTH_USER, user);
			}
		} else {
			//Se nao for, request nao autorizado
			serverResponse = new ServerResponse();
			serverResponse.setStatus(Response.Status.FORBIDDEN.getStatusCode());
			return serverResponse;
		}

		
		return serverResponse;
	}
	/**
	 * 
	 * @return
	 */
	private ServerResponse checkPermissions(HttpRequest request,ResourceMethod resMethod) {
		return null;
	}
}

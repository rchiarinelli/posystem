/**
 * 
 */
package br.valinorti.posystem.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import br.valinorti.posystem.entity.User;
import br.valinorti.posystem.security.interceptor.POSystemSecurityInterceptor;

/**
 * @author leafar
 *
 */
public abstract class BaseRESTService implements RESTService {

	@Context
	private HttpServletRequest request;
	
	/**
	 * Seta o request
	 * 
	 * @param request
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.RESTService#getAuthUser()
	 */
	@Override
	public User getAuthUser() {
		User user = (User) this.request.getSession().getAttribute(POSystemSecurityInterceptor.AUTH_USER);
		return user;
	}
}

/**
 * 
 */
package br.valinorti.posystem.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.posystem.service.view.UtilityViewBean;
import br.valinorti.util.SystemMessages;

/**
 * Classe com servicos utilitários do sistema.
 * 
 * @author Rafael Chiarinelli
 *
 */
@Path("/{subscriber}/utilities")
public class UtilsService extends BaseRESTService {
	
	@Context
	private HttpServletRequest request;
	
	@GET
	@Path("current_server_date")
	@Produces("application/json")
	@BadgerFish
	public UtilityViewBean getCurrentServerDate() {
		String datePattern = SystemMessages.getMessage("date_pattern");
		DateFormat df = new SimpleDateFormat(datePattern);
		df.setLenient(false);
		Date date = new Date();
		date.setTime(System.currentTimeMillis());
		//Formatar a data
		UtilityViewBean viewBean = new UtilityViewBean(df.format(date));
		return viewBean;
	}
	
	@GET
	@Path("/logoff")
	@Produces("application/json")
	@BadgerFish
	public ServiceResponse logoff(){
		ServiceResponse response = new ServiceResponse();
		if (this.request==null) {
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} else {
			this.request.getSession().invalidate();
			response.setStatus(ServiceResponseEnum.OK.ordinal());
		}
		return response;
	}
}
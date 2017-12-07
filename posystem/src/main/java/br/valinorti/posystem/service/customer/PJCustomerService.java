/**
 * 
 */
package br.valinorti.posystem.service.customer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.exception.POSystemException;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.service.customer.view.AddPJCustomerViewBean;
import br.valinorti.posystem.service.customer.view.CustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PJCustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PJCustomerViewBean;
import br.valinorti.posystem.service.customer.view.UpdatePJCustomerViewBean;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;
import br.valinorti.posystem.service.view.validator.ViewBeanValidator;

/**
 * Classe usada para publicar servicos de cliente pessoa juridica(PJ).
 * @author leafar
 *
 */
@Path("/{subscriber}/pjcustomer")
public class PJCustomerService extends CustomerService {
	
	private static Logger logger = Logger.getLogger(PJCustomerService.class);
	
	@POST
	@Path("/add")
	@Produces("application/json")
	@Consumes("application/json")
	@BadgerFish
	public CustomerServiceResponse addCustomer(@BadgerFish AddPJCustomerViewBean viewBean) {
		CustomerServiceResponse response = new CustomerServiceResponse();
		try {
			ViewBeanValidator validator = new ViewBeanValidator();
			validator.validate(viewBean);
			Customer customer = ViewBeanHelper.getPJCustomerViewBeanHelper().convertViewBeanToEntity(viewBean);
			Customer result =  super.addCustomer(customer);
			PJCustomerViewBean resultViewBean = ViewBeanHelper.getPJCustomerViewBeanHelper().convertEntityToViewBean((PJCustomer)result);
			response.setStatus(ServiceResponseEnum.OK.ordinal());
			response.setViewBeanResponse(resultViewBean);
			response.setMessage("Cadastro OK.");
		} catch (POSystemException exc) {
			logger.error(exc);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getSystemMessages());
		} catch (Exception exc) {
			logger.error(exc);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getMessage());
		}
		return response;
	}
	@POST
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")
	@BadgerFish
	public CustomerServiceResponse updateCustomer(@BadgerFish UpdatePJCustomerViewBean viewBean) {
		CustomerServiceResponse resp = new CustomerServiceResponse();
		try {
			ViewBeanValidator validator = new ViewBeanValidator();
			validator.validate(viewBean);
			PJCustomer customerNewVal = ViewBeanHelper.getPJCustomerViewBeanHelper().convertViewBeanToEntity(viewBean);
			PJCustomer custOldVal = (PJCustomer) this.getCustomer(customerNewVal.getId(), PJCustomer.class);
			PJCustomer mergedCustomer = ViewBeanHelper.getPJCustomerViewBeanHelper().generateEntityWithChanges(custOldVal, customerNewVal);
			PJCustomer updatedCustomer = (PJCustomer) super.updateCustomer(mergedCustomer);
			resp.setStatus(ServiceResponseEnum.OK.ordinal());
			resp.setMessage("Dados do cliente atualizados com sucesso.");
			resp.setViewBeanResponse(ViewBeanHelper.getPJCustomerViewBeanHelper().convertEntityToViewBean(updatedCustomer));
		} catch (POSystemException exc) {
			logger.error(exc);
			resp.setStatus(ServiceResponseEnum.FAILED.ordinal());
			resp.setMessage(exc.getSystemMessages());
		} catch (Exception exc) {
			logger.error(exc);
			resp.setStatus(ServiceResponseEnum.FAILED.ordinal());
			resp.setMessage(exc.getMessage());
		}
		return resp;
	}
	
	@GET
	@Path("/get/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish	
	public PJCustomerServiceResponse getCustomerById(@PathParam("id") String id) {
		PJCustomerServiceResponse response = new PJCustomerServiceResponse();
		try {
			if (!StringUtils.isNumeric(id)) {
				response.setMessage("ID passado &eacute; inv&aacute;lido");
				response.setStatus(ServiceResponseEnum.FAILED.ordinal());
				return response;
			}
			PJCustomer pjCustomer = (PJCustomer) super.getCustomer(Integer.parseInt(id), PJCustomer.class);
			PJCustomerViewBean viewBeanRes = ViewBeanHelper.getPJCustomerViewBeanHelper().convertEntityToViewBean(pjCustomer);
			response.setViewBean(viewBeanRes);
			response.setStatus(ServiceResponseEnum.OK.ordinal());
			response.setMessage("Cliente PJ encontrado.");
		} catch (POSystemException exc) {
			logger.error(exc);
			response.setMessage(exc.getMessage());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} catch (Exception exc) {
			logger.error(exc);
			response.setMessage(exc.getMessage());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		}
		return response;
	}
}

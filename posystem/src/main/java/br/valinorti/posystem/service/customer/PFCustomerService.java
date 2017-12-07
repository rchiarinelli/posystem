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
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.service.customer.view.AddPFCustomerViewBean;
import br.valinorti.posystem.service.customer.view.CustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PFCustomerServiceResponse;
import br.valinorti.posystem.service.customer.view.PFCustomerViewBean;
import br.valinorti.posystem.service.customer.view.UpdatePFCustomerViewBean;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;
import br.valinorti.posystem.service.view.validator.ViewBeanValidator;

/**
 * Classe usada para publicar servicos de cliente pessoa fisica(PF).
 * 
 * @author leafar
 *
 */
@Path("/{subscriber}/pfcustomer")
public class PFCustomerService extends CustomerService {
	
	private static Logger logger = Logger.getLogger(PFCustomerService.class);
	
	/**
	 * Recebe uma string JSON no formato {"foo":{"property1":"value"},{"property2","value"}},
	 * e executa o servico de adicionar um novo cliente.
	 * @param jsonString
	 * @return
	 */
	@POST
	@Path("/add")
	@Produces("application/json")
	@Consumes("application/json")
	@BadgerFish
	public CustomerServiceResponse addCustomer(@BadgerFish AddPFCustomerViewBean viewBean) {
		logger.debug("Adding customer");
		CustomerServiceResponse serviceResponse = new CustomerServiceResponse();
		try {
			//validar o viewbean
			ViewBeanValidator validator = new ViewBeanValidator();
			validator.validate(viewBean);
			Customer customer = ViewBeanHelper.getPFCustomerHelper().convertViewBeanToEntity((PFCustomerViewBean)viewBean);
			Customer result =  super.addCustomer(customer);
			PFCustomerViewBean resultViewBean = ViewBeanHelper.getPFCustomerHelper().convertEntityToViewBean((PFCustomer)result);
			serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
			serviceResponse.setViewBeanResponse(resultViewBean);
			serviceResponse.setMessage("Cadastro OK.");
		} catch(POSystemException exc) {
			logger.error(exc);
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
			serviceResponse.setMessage(exc.getSystemMessages());
		} catch(Exception exc) {
			logger.error(exc);
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
			serviceResponse.setMessage(exc.getMessage());
		}		
		return serviceResponse;		
	}
	/**
	 * Recebe uma string JSON no formato {"foo":{"property1":"value"},{"property2","value"}},
	 * e executa o servico de atualizar um novo cliente.O parametro JSON deve conter
	 * somente os campos alterados no objeto e mais o ID.
	 * @param jsonString
	 * @return
	 */	
	@POST
	@Path("/update")
	@Produces("application/json")
	@Consumes("application/json")	
	@BadgerFish
	public ServiceResponse updateCustomer(UpdatePFCustomerViewBean viewBean) {
		ServiceResponse response = new ServiceResponse();
		try {
			PFCustomer customer = (PFCustomer) ViewBeanHelper.getPFCustomerHelper().convertViewBeanToEntity(viewBean);
			PFCustomer originalCustomer = (PFCustomer) this.getCustomer(customer.getId(), PFCustomer.class);
			Customer customerToUpdate = ViewBeanHelper.getPFCustomerHelper()
					.generateEntityWithChanges(originalCustomer, customer);
			this.updateCustomer(customerToUpdate);
			response.setStatus(ServiceResponseEnum.OK.ordinal());
			response.setMessage("Cliente atualizado.");
		} catch (POSystemException poex) {
			logger.error(poex);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(poex.getSystemMessages());
		} catch (Exception exc) {
			logger.error(exc);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getMessage());
		}
		return response;
	}
	/* (non-Javadoc)
	 * @see br.valinorti.posystem.service.customer.CustomerService#getCustomer(java.lang.String)
	 */
	@GET
	@Path("/get/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish	
	public PFCustomerServiceResponse getCustomerByID(@PathParam("id") String id) {
		logger.debug("Loading customer");
		PFCustomerServiceResponse response = new PFCustomerServiceResponse();
		if (!StringUtils.isNumeric(id)) {
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage("ID inv&aacute;lido.");
			return response;
		}
		try {
			PFCustomer customer = (PFCustomer) super.getCustomer(Integer.valueOf(id), PFCustomer.class);
			response.setStatus(ServiceResponseEnum.OK.ordinal());
			response.setMessage("Customer loaded.");
			logger.debug("Customer loaded.");
			response.setViewBean(ViewBeanHelper.getPFCustomerHelper().convertEntityToViewBean(customer));
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
	
	
}

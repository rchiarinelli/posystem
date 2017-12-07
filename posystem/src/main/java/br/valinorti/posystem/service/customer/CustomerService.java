/**
 * 
 */
package br.valinorti.posystem.service.customer;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.exception.POSystemException;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.ExecuteFilterCommand;
import br.valinorti.posystem.command.FilterCustomerCommand;
import br.valinorti.posystem.command.GetCustomerCommand;
import br.valinorti.posystem.command.ListCustomersWithBillingsCommand;
import br.valinorti.posystem.command.SaveCustomerCommand;
import br.valinorti.posystem.command.UpdateCustomerCommand;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.service.BaseRESTService;
import br.valinorti.posystem.service.customer.view.CustomerFilterViewBean;
import br.valinorti.posystem.service.customer.view.CustomerListViewBean;
import br.valinorti.posystem.service.customer.view.CustomerViewBean;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;

/**
 * @author Rafael Chiarinelli
 * 
 */
@Path("/{subscriber}/customer")
public class CustomerService extends BaseRESTService {

	private static Logger logger = Logger.getLogger(CustomerService.class);
	
//	@Context
//	private SecurityContext securityContext;
	
	
	@GET
	@Path("list")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	//@RolesAllowed(value={"read"})
	
	public CustomerListViewBean listCustomers() {
		Filter filter = new Filter();
		filter.setClazz(Customer.class);
		filter.addArgument(FilterConditions.EQUALS, "status", CustomerStatus.ACTIVE);
		filter.addArgument(FilterConditions.EQUALS, "subscriber.id", this.getAuthUser().getSubscriber().getId());
		
		HibernateExecutor<List<Customer>> executor = new HibernateExecutor<List<Customer>>();
		List<Customer> customers = executor.executeCommand(new FilterCustomerCommand(filter));		
		
		//Converter para view bean
		CustomerListViewBean viewBeans = new CustomerListViewBean();
		viewBeans.setViewList(ViewBeanHelper.getCustomerViewBeanHelper().convertEntityListToViewList(customers));
		
		return viewBeans;
	}
	
	@POST
	@Path("filter")
	@Consumes("application/json")
	@Produces("text/plain")
	@GZIP
	public String filterCustomers(@BadgerFish CustomerFilterViewBean filterViewBean) {
		logger.debug("Filtrando Cliente(s)");
		Filter filter = ViewBeanHelper.getCustomerViewBeanHelper().convertFilterViewBeanToFilter(filterViewBean);
		filter.addArgument(FilterConditions.EQUALS, "status", CustomerStatus.ACTIVE);
		List<Customer> results = this.listByFilter(filter);
		List<CustomerViewBean> viewBeanList = ViewBeanHelper.getCustomerViewBeanHelper().convertEntityListToViewList(results);
		JSONObject jsonResponse = ViewBeanHelper.getJSONViewHelper().convertBeanListToJSONGridItems(viewBeanList);
		logger.debug("Cliente(s) encontrado(s): " + results.size());
		return jsonResponse.toString();
	}
	

	@POST
	@Path("/delete")
	@Consumes("application/json")
	@Produces("application/json")
	@BadgerFish	
	public ServiceResponse delete(final CustomerViewBean customerViewBean) {
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			if (customerViewBean.getId()==null
					|| customerViewBean.getId()<=0){
				serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
				serviceResponse.setMessage("Id do cliente &eacute; inv&aacute;lido.");
			}
			if (serviceResponse.getStatus()==null) {
				logger.debug("Deleting customer");
				HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
				Filter filter = new Filter();
				filter.setClazz(Customer.class);
				filter.addArgument(FilterConditions.EQUALS, "id", customerViewBean.getId());
				filter.addArgument(FilterConditions.EQUALS, "subscriber.id", this.getAuthUser().getSubscriber().getId());				
				
				Customer customer = executor.executeCommand(new GetCustomerCommand(filter));				
				//Mudar o status para inativo
				customer.setStatus(CustomerStatus.INACTIVE);
				this.updateCustomer(customer);
				serviceResponse.setMessage("Cliente removido com sucesso");
				serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
				logger.debug("Customer deleted.");
			}
		} catch (POSystemException exc) {
			logger.error(exc);
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
			serviceResponse.setMessage(exc.getSystemMessages());
		} catch (Exception exc) {
			logger.error(exc);
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
			serviceResponse.setMessage(exc.getMessage());
		}
		return serviceResponse;
	}
	
	/**
	 * 
	 * @param viewBean
	 * @return
	 */
	public Customer addCustomer(Customer customer) {
		logger.debug("Storing customer");
		customer.setStatus(CustomerStatus.ACTIVE);
		//Salvar
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		customer.setSubscriber(this.getAuthUser().getSubscriber());
		Customer result = executor.executeCommand(new SaveCustomerCommand(customer));
		logger.debug("Customer stored.");
		return result;
	}
	
	/**
	 * 
	 * @param customer
	 * @return
	 */
	public Customer updateCustomer(Customer customer) {
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		Customer result = executor.executeCommand(new UpdateCustomerCommand(customer));
		return result;
	}
	/**
	 * 
	 * @param id
	 * @param customerClazz
	 * @return
	 */
	public Customer getCustomer(Integer id, Class<? extends Customer> customerClazz) {
		logger.debug("Loading customer");
		Customer customer = null;
		try {
			HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
			Filter filter = new Filter();
			filter.setClazz(customerClazz);
			filter.addArgument(FilterConditions.EQUALS, "id", id);
			filter.addArgument(FilterConditions.EQUALS, "subscriber.id", this.getAuthUser().getSubscriber().getId());
			customer = executor.executeCommand(new GetCustomerCommand(filter));
		} catch (POSystemException exc) {
			logger.error(exc);
		} catch (Exception exc) {
			logger.error(exc);
		}
		return customer;
	}
	
	
	@GET
	@Path("list/customerswithbillings")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	public CustomerListViewBean listCustomersWithBillings() {
		HibernateExecutor<List<Customer>> executor = new HibernateExecutor<List<Customer>>();
		Integer subscriberId = this.getAuthUser().getSubscriber().getId();
		List<Customer> customers = executor.executeCommand(new ListCustomersWithBillingsCommand(subscriberId));		
		//Converter para view bean
		CustomerListViewBean viewBeans = new CustomerListViewBean();
		viewBeans.setViewList(ViewBeanHelper.getCustomerViewBeanHelper().convertEntityListToViewList(customers));

		return viewBeans;
	}
	
	
	/**
	 * 
	 * @param filter
	 * @return
	 */
	public List<Customer> listByFilter(Filter filter) {
		HibernateExecutor<List<Customer>> executor = new HibernateExecutor<List<Customer>>();
		filter.addArgument(FilterConditions.EQUALS, "subscriber.id", this.getAuthUser().getSubscriber().getId());
		List<Customer> results = executor.executeCommand(new ExecuteFilterCommand<Customer>(filter));
		return results;

	}
}

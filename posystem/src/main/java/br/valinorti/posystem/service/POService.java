/**
 * 
 */
package br.valinorti.posystem.service;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.exception.POSystemException;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.FilterPOCommand;
import br.valinorti.posystem.command.GetOrderCommand;
import br.valinorti.posystem.command.SavePOCommand;
import br.valinorti.posystem.command.UpdateOrderCommand;
import br.valinorti.posystem.command.UpdatePOCommand;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.entity.POStatus;
import br.valinorti.posystem.entity.ProductionOrder;
import br.valinorti.posystem.service.po.view.AddPOViewBean;
import br.valinorti.posystem.service.po.view.POCode;
import br.valinorti.posystem.service.po.view.PODashboardValues;
import br.valinorti.posystem.service.po.view.POFilterViewBean;
import br.valinorti.posystem.service.po.view.POServiceResponse;
import br.valinorti.posystem.service.po.view.POViewBean;
import br.valinorti.posystem.service.po.view.UpdatePOViewBean;
import br.valinorti.posystem.service.view.KeyValueBean;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;
import br.valinorti.posystem.service.view.validator.ViewBeanValidator;
import br.valinorti.util.SystemMessages;

/**
 * @author Rafael Chiarinelli
 *
 */
@Path("/{subscriber}/po")
public class POService extends BaseRESTService {
	/**
	 * 
	 */
	private static Logger logger = Logger.getLogger(POService.class);
	
	@GET
	@Path("code")
	@Produces("application/json")
	@BadgerFish
	public POCode getNewRequestCode() {
		logger.debug("Gerando novo codigo da ordem de servico.");
		HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
		Filter filter = new Filter();
		filter.setClazz(ProductionOrder.class);
		List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
		NumberFormat formatter = new DecimalFormat("000000"); 
		String code = formatter.format(results.size()+1);
		logger.debug("Ordem de servi�o gerado com sucesso.");
		return new POCode(code);
	}
	
	@POST
	@Path("add")
	@Produces("application/json")
	@Consumes("application/json")
	@BadgerFish	
	public ServiceResponse addPO(AddPOViewBean viewBean) {
		logger.debug("Adding PO.");
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			//validar o viewbean
			ViewBeanValidator validator = new ViewBeanValidator();
			validator.validate(viewBean);
			ProductionOrder po = ViewBeanHelper.getPOViewHelper().convertViewBeanToPO(viewBean);
			po.setOpenDate(new Date());
			po.setStatus(POStatus.PLANNING);
			po.setSubscriber(this.getAuthUser().getSubscriber());
			
			//Salvar
			HibernateExecutor<ProductionOrder> executor = new HibernateExecutor<ProductionOrder>();
			executor.executeCommand(new SavePOCommand(po));
			serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
			serviceResponse.setMessage("PO stored.");
			logger.debug("PO stored.");
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
	
	@GET
	@Path("/get/statusvalues")
	@Produces("application/json")
	@BadgerFish
	public KeyValueBean[] getStatusValues(){
		POStatus[] statusArray = POStatus.values();
		KeyValueBean[] response = new KeyValueBean[statusArray.length];
		KeyValueBean resp = null;
		for (int i = 0; i < statusArray.length; i++) {
			resp = new KeyValueBean();
			resp.setValue(String.valueOf(statusArray[i].ordinal()));
			resp.setText(statusArray[i].getStatusValue());
			response[i] = resp;
		}
		return response;
	}	
	
	@POST
	@Path("filter")
	@Consumes("application/json")
	@Produces("text/plain")
	public String filterPO(@BadgerFish POFilterViewBean filterViewBean) {
		logger.debug("Filtrando OS(s)");
		Filter filter = ViewBeanHelper.getPOViewHelper().convertFilterViewBeanToFilter(filterViewBean);
		HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
		List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
		List<POViewBean> viewBeanList = ViewBeanHelper.getPOViewHelper().convertPOListToViewBeanList(results);
		JSONObject jsonResponse = ViewBeanHelper.getJSONViewHelper().convertBeanListToJSONGridItems(viewBeanList);
		logger.debug("OS(s) filtradas: " );
		return jsonResponse.toString();
	}
	
	@GET
	@Path("/get/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public POServiceResponse getPO(@PathParam("id") String id) {
		POServiceResponse response = new POServiceResponse();
		try {
			Filter filter = new Filter();
			filter.setClazz(ProductionOrder.class);
			filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(id));
			HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
			List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
			if (results.isEmpty()) {
				response.setMessage("Ordem de servi�o n�o encontrada.");
				response.setStatus(ServiceResponseEnum.FAILED.ordinal());
				logger.debug("Ordem de servi�o n�o encontrada");
			} else {
				logger.debug("Ordem de servi�o encontrada");
				response.setMessage("Ordem de servi�o recuperada.");
				response.setStatus(ServiceResponseEnum.OK.ordinal());
				ProductionOrder po = results.get(0);
				POViewBean viewBean = ViewBeanHelper.getPOViewHelper().convertPOToViewBean(po);
				response.setViewBean(viewBean);
			}
		} catch (POSystemException exc) {
			logger.error(exc);
			response.setMessage(exc.getSystemMessages());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} catch (Exception exc) {
			logger.error(exc);
			response.setMessage(exc.getMessage());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		}
		return response;
	}
	@POST
	@Path("update")
	@Produces("application/json")
	@Consumes("application/json")
	@BadgerFish
	public ServiceResponse updatePO(@BadgerFish UpdatePOViewBean viewBean) {
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			//Tranformar viewBean em um entity
			ProductionOrder po = ViewBeanHelper.getPOViewHelper().convertViewBeanToPO(viewBean);
			//Recuperar o PO original
			Filter filter = new Filter();
			filter.setClazz(ProductionOrder.class);
			filter.addArgument(FilterConditions.EQUALS, "id", po.getId());
			HibernateExecutor<List<ProductionOrder>> filterExecutor = new HibernateExecutor<List<ProductionOrder>>();
			List<ProductionOrder> poList = filterExecutor.executeCommand(new FilterPOCommand(filter));
			//Setar o numero da PO no bean a ser atualizado
			po.setPoNumber(poList.get(0).getPoNumber());
			po.setStatus(poList.get(0).getStatus());
			HibernateExecutor<ProductionOrder> executor = new HibernateExecutor<ProductionOrder>();
			executor.executeCommand(new UpdatePOCommand(po));
			serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
			serviceResponse.setMessage("Ordem de servi�o atualizada.");
			logger.debug("Ordem de servi�o atualizada.");
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
	@GET
	@Path("/cancheckout/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public KeyValueBean canCheckout(@PathParam("id") String id) {
		KeyValueBean response = new KeyValueBean();
		Filter filter = new Filter();
		filter.setClazz(ProductionOrder.class);
		filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(id));
		HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
		List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
		if (!results.isEmpty()) {
			ProductionOrder po = results.get(0);
			response.setText("status");
			if ((po.getStatus()==POStatus.EXECUTING )|| 
					(po.getStatus()==POStatus.PLANNING)) {
				response.setValue("1");
			} else {
				response.setValue("0");
			}
		}
		return response;
	}
	@GET
	@Path("/canfinalize/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public KeyValueBean canFinalize(@PathParam("id") String id) {
		KeyValueBean response = new KeyValueBean();
		Filter filter = new Filter();
		filter.setClazz(ProductionOrder.class);
		filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(id));
		HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
		List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
		if (!results.isEmpty()) {
			ProductionOrder po = results.get(0);
			response.setText("status");
			if ((po.getStatus()==POStatus.EXECUTING)){
				response.setValue("1");
			} else {
				response.setValue("0");
			}
		}
		return response;
	}
	
	@GET
	@Path("/checkout/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public ServiceResponse checkout(@PathParam("id") String id) {
		ServiceResponse response = new ServiceResponse();
		try {
			Filter filter = new Filter();
			filter.setClazz(ProductionOrder.class);
			filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(id));
			HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
			List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
			if (results.isEmpty()) {
				response.setStatus(ServiceResponseEnum.FAILED.ordinal());
				response.setMessage("Ordem de servi�o n�o encontrada.");
			} else {
				ProductionOrder po = results.get(0);
				if (po.getStatus()==POStatus.DONE){
					response.setStatus(ServiceResponseEnum.FAILED.ordinal());
					response.setMessage("Ordem de servi&ccedil;o j&aacute; est&aacute; concluida.");
					logger.debug("PO is already ready.");
				} else if (po.getStatus()==POStatus.PLANNING
						|| po.getStatus()==POStatus.EXECUTING){
					//Mudar status da OS
					po.setStatus(POStatus.EXECUTING);
					HibernateExecutor<ProductionOrder> updateExecutor = new HibernateExecutor<ProductionOrder>();
					updateExecutor.executeCommand(new UpdatePOCommand(po));
					//Mudar o status do pedido para executando, caso for aberto.
					HibernateExecutor<Order> requestExec = new HibernateExecutor<Order>();
					Filter reqFilter = new Filter();
					reqFilter.setClazz(Order.class);
					reqFilter.addArgument(FilterConditions.EQUALS, "id", po.getOrder().getId());
					reqFilter.addArgument(FilterConditions.EQUALS, "status", OrderStatus.OPEN);
					Order request = requestExec.executeCommand(new GetOrderCommand(reqFilter));
					if (request!=null) {
						request.setStatus(OrderStatus.EXECUTING);
						requestExec.executeCommand(new UpdateOrderCommand(request));
					}
					response.setStatus(ServiceResponseEnum.OK.ordinal());
					response.setMessage("Ordem de servi&ccedil;o emitida");
					logger.debug("PO checkouted");
				}
			}
		} catch (POSystemException exc) {
			logger.error(exc);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getSystemMessages());
		} catch (Exception exc){
			logger.error(exc);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getMessage());
		}
		return response;
	}
	
	@POST
	@Path("finalize")
	@Consumes("application/json")
	@Produces("application/json")
	@BadgerFish
	public ServiceResponse finalizePO(POViewBean viewBean) {
		ServiceResponse response = new ServiceResponse();
		try {
			Filter filter = new Filter();
			filter.setClazz(ProductionOrder.class);
			filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(viewBean.getPoId()));
			filter.addArgument(FilterConditions.EQUALS, "status", POStatus.EXECUTING);
			HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
			List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
			if (results.isEmpty()) {
				response.setStatus(ServiceResponseEnum.FAILED.ordinal());
				response.setMessage("Ordem de servi&ccedil;o n&atilde;o encontrada.");
			} else {
				ProductionOrder po = results.get(0);
				//Mudar status da OS
				po.setStatus(POStatus.DONE);
				po.setDeliverDate(new Date());
				HibernateExecutor<ProductionOrder> updateExecutor = new HibernateExecutor<ProductionOrder>();
				updateExecutor.executeCommand(new UpdatePOCommand(po));
				response.setStatus(ServiceResponseEnum.OK.ordinal());
				response.setMessage("Ordem de servi&ccedil;o conclu&iacute;da");
			}
		} catch (POSystemException exc) {
			logger.error(exc);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getSystemMessages());
		} catch (Exception exc){
			logger.error(exc);
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getMessage());
		}
		return response;		
	}
	
	@GET
	@Path("/canedit/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public KeyValueBean canEdit(@PathParam("id") String id) {
		KeyValueBean response = new KeyValueBean();
		Filter filter = new Filter();
		filter.setClazz(ProductionOrder.class);
		filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(id));
		HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
		List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
		if (!results.isEmpty()) {
			ProductionOrder po = results.get(0);
			response.setText("status");
			if ((po.getStatus()==POStatus.PLANNING)
					|| (po.getStatus()==POStatus.EXECUTING)){
				response.setValue("1");
			} else {
				response.setValue("0");
			}
		}
		return response;
	}	
	@GET
	@Path("/get/dashboardvalues/{fromDate}&{toDate}")
	@Produces("application/json")
	@BadgerFish
	public PODashboardValues getPODashboardValues(@PathParam("fromDate") String fromDate, @PathParam("toDate") String toDate) {
		
		String dashboardDatePattern = SystemMessages.getMessage("dashboard_date_pattern");
		DateFormat df = new SimpleDateFormat(dashboardDatePattern);

		String startDateStr = StringUtils.replace(fromDate, "-", "/");
		String endDateStr = StringUtils.replace(toDate, "-", "/");
		
		try {
			
			Date startDate = df.parse(startDateStr);
			Date endDate = df.parse(endDateStr);
			
			df = new SimpleDateFormat(SystemMessages.getMessage("filter_date_pattern"));
			
			Filter planningFilter = new Filter();
			planningFilter.setClazz(ProductionOrder.class);
			planningFilter.addArgument(FilterConditions.EQUALS, "status", POStatus.PLANNING);
			planningFilter.addArgument(FilterConditions.BETWEEN, "openDate",
					new Date[] { startDate, endDate });			
			
			Filter prodFilter = new Filter();
			prodFilter.setClazz(ProductionOrder.class);
			prodFilter.addArgument(FilterConditions.EQUALS, "status", POStatus.EXECUTING);
			prodFilter.addArgument(FilterConditions.BETWEEN, "openDate",
					new Date[] { startDate, endDate });			
			
			Filter concFilter = new Filter();
			concFilter.setClazz(ProductionOrder.class);
			concFilter.addArgument(FilterConditions.EQUALS, "status", POStatus.DONE);
			concFilter.addArgument(FilterConditions.BETWEEN, "openDate",
					new Date[] { startDate, endDate });			

			
			HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
			
			List<ProductionOrder> planningResults = executor.executeCommand(new FilterPOCommand(planningFilter));
			List<ProductionOrder> prodResults = executor.executeCommand(new FilterPOCommand(prodFilter));
			List<ProductionOrder> concludedResults = executor.executeCommand(new FilterPOCommand(concFilter));
			
			PODashboardValues dashboard = new PODashboardValues();
			dashboard.getPlanning().setValue(String.valueOf(planningResults.size()));
			dashboard.getProduction().setValue(String.valueOf(prodResults.size()));
			dashboard.getConclued().setValue(String.valueOf(concludedResults.size()));
			
			return dashboard;
		} catch (ParseException exc) {
			throw new POSystemException(exc);
		}
		
	}
	@GET
	@Path("/get/byorder/{id}")
	@Consumes("text/plain")
	@Produces("text/plain")
	@BadgerFish
	public String loadPOByRequest(@PathParam("id") String id) {
		Filter filter = new Filter();
		filter.setClazz(ProductionOrder.class);
		filter.addArgument(FilterConditions.EQUALS, "order.id", Long.parseLong(id));
		
		HibernateExecutor<List<ProductionOrder>> executor = new HibernateExecutor<List<ProductionOrder>>();
		List<ProductionOrder> results = executor.executeCommand(new FilterPOCommand(filter));
		
		List<POViewBean> poViewBeanList = ViewBeanHelper.getPOViewHelper().convertPOListToViewBeanList(results);
		JSONObject response = ViewBeanHelper.getJSONViewHelper().convertBeanListToJSONGridItems(poViewBeanList);
		
		return response.toString();
	}
}

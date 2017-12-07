/**
 * 
 */
package br.valinorti.posystem.service.rest;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.exception.POSystemException;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.FilterOrderCommand;
import br.valinorti.posystem.command.GetOrderCommand;
import br.valinorti.posystem.command.ListOrderCommand;
import br.valinorti.posystem.command.SaveOrderCommand;
import br.valinorti.posystem.command.UpdateOrderCommand;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.entity.POStatus;
import br.valinorti.posystem.entity.ProductionOrder;
import br.valinorti.posystem.service.BaseRESTService;
import br.valinorti.posystem.service.OrderService;
import br.valinorti.posystem.service.order.view.AddRequestViewBean;
import br.valinorti.posystem.service.order.view.ProcessBillingValuesServiceResponse;
import br.valinorti.posystem.service.order.view.ProcessBillingValuesViewBean;
import br.valinorti.posystem.service.order.view.ProcessBillingViewBean;
import br.valinorti.posystem.service.order.view.RequestCode;
import br.valinorti.posystem.service.order.view.RequestDashboardViewBean;
import br.valinorti.posystem.service.order.view.RequestFilterViewBean;
import br.valinorti.posystem.service.order.view.RequestListViewBean;
import br.valinorti.posystem.service.order.view.RequestServiceResponse;
import br.valinorti.posystem.service.order.view.RequestViewBean;
import br.valinorti.posystem.service.order.view.UpdateRequestViewBean;
import br.valinorti.posystem.service.view.KeyValueBean;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;
import br.valinorti.posystem.service.view.validator.ViewBeanValidator;
import br.valinorti.util.SystemMessages;

/**
 * @author leafar
 *
 */
@Path("/{subscriber}/order")
public class OrderRestService extends BaseRESTService {
	
	private static Logger logger = Logger.getLogger(OrderService.class);
	/**
	 * 
	 * @param viewBean
	 */
	@POST
	@Path("add")
	@Consumes("application/json")
	@Produces("application/json")
	@BadgerFish
	public ServiceResponse addOrder(@BadgerFish AddRequestViewBean viewBean){
		logger.debug("Adding a request");
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			//validar o viewbean
			ViewBeanValidator validator = new ViewBeanValidator();
			validator.validate(viewBean);
			Order order = ViewBeanHelper.getRequestHelper().convertViewBeanToRequest(viewBean);
			//Recuperar o subscriber associado ao usuario logado
			order.setSubscriber(this.getAuthUser().getSubscriber());
			
			//Salvar
			HibernateExecutor<Order> executor = new HibernateExecutor<Order>();
			executor.executeCommand(new SaveOrderCommand(order));
			serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
			serviceResponse.setMessage("Request added.");
			logger.debug("Request added");
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
	@Path("orderCode")
	@Produces("application/json")
	@BadgerFish
	public RequestCode getNewOrderCode() {
		logger.debug("Gerando novo codigo do pedido.");
		HibernateExecutor<List<Order>> executor = new HibernateExecutor<List<Order>>();
		List<Order> requestResults = executor.executeCommand(new ListOrderCommand());
		String requestCode = ViewBeanHelper.getRequestHelper().buildCode(requestResults.size());
		logger.debug("Codigo do pedido gerado com sucesso.");
		return new RequestCode(requestCode);
	}
	
	@POST
	@Path("filter")
	@Consumes("application/json")
	@Produces("text/plain")
	@GZIP
	public String filterOrder(@BadgerFish RequestFilterViewBean filterViewBean) {
		logger.debug("Filtrando pedidos");
		Filter filter = ViewBeanHelper.getRequestFilterHelper().convertFilterRequestToFilter(filterViewBean);
		HibernateExecutor<List<Order>> executor = new HibernateExecutor<List<Order>>();
		List<Order> results = executor.executeCommand(new FilterOrderCommand(filter));
		List<RequestViewBean> viewBeanList = ViewBeanHelper.getRequestHelper().convertRequestListToRequestViewBeanList(results);
		JSONObject jsonResponse = ViewBeanHelper.getJSONViewHelper()
						.convertBeanListToJSONGridItems(viewBeanList);
		logger.debug("Pedidos filtrados:" + viewBeanList.size());		
		return jsonResponse.toString();
	}
	
	@GET
	@Path("/get/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public RequestServiceResponse getOrder(@PathParam("id") String id) {
		logger.debug("Recuperando pedido pedido.");

		RequestServiceResponse serviceResponse = new RequestServiceResponse();
		try {
			OrderService orderService = new OrderService();
			Order request = orderService.getOrderById(id);
			RequestViewBean viewBean = ViewBeanHelper.getRequestHelper().convertRequestToViewBean(request);
			serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
			serviceResponse.setMessage("Pedido recuperado com sucesso.");
			serviceResponse.setViewBeanResponse(viewBean);
			logger.debug("Pedido recuperado com sucesso.");
		} catch (POSystemException exc) {
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
			serviceResponse.setMessage(exc.getSystemMessages());
		} catch (Exception exc) {
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
			serviceResponse.setMessage(exc.getMessage());
		}

		return serviceResponse;
	}
	
	@GET
	@Path("/get/{id}/pos")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public String getPOs(@PathParam("id") String requestId){
		return null;
	}
	@PUT
	@Path("/update")
	@Consumes("application/json")
	@Produces("application/json")
	@BadgerFish
	public ServiceResponse updateOrder(@BadgerFish UpdateRequestViewBean viewBean) {
		logger.debug("Atualizando pedido");
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			//Validar viewBean
			ViewBeanValidator validator = new ViewBeanValidator();
			validator.validate(viewBean);
			HibernateExecutor<Order> executor = new HibernateExecutor<Order>();			
			Filter filter = new Filter();
			filter.setClazz(Order.class);
			filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(viewBean.getRequestId()));
			Order request = executor.executeCommand(new GetOrderCommand(filter));
			Order newRequest = ViewBeanHelper.getRequestHelper().convertViewBeanToRequest(viewBean);
			request.setDescription(newRequest.getDescription());
			request.setPrice(newRequest.getPrice());
			request.setQuantity(newRequest.getQuantity());
			request.setDeliverDate(newRequest.getDeliverDate());
			//Salvar
			executor.executeCommand(new UpdateOrderCommand(request));
			serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
			serviceResponse.setMessage("Pedido atualizado.");
			logger.debug("Pedido atualizado.");
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
	public KeyValueBean[] getStatus(){
		OrderStatus[] statusArray = OrderStatus.values();
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
	
	@PUT
	@Path("/close/{id}")
	@Produces("application/json")
	@BadgerFish
	public RequestServiceResponse closeOrder(@PathParam("id") String id){
		RequestServiceResponse response = new RequestServiceResponse();
		if (id==null || id.equals("")){
			response.setMessage("Parametro informado � invalido");
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} else {
			//Checar se � um valor valido
			try {
				Long requestId = Long.parseLong(id);
				HibernateExecutor<Order> executor = new HibernateExecutor<Order>();
				Filter filter = new Filter();
				filter.setClazz(Order.class);
				filter.addArgument(FilterConditions.EQUALS, "id", requestId);
				Order request = executor.executeCommand(new GetOrderCommand(filter));
				//Checar se todas a ordens de servi�o relacionadas est�o fechadas.
				Set<ProductionOrder> produtionsOrders = request.getProductionOrders();
				NumberFormat formatter = new DecimalFormat("000000");
				StringBuilder message = null;
				String code = null;
				for (ProductionOrder productionOrder : produtionsOrders) {
					if (productionOrder.getStatus()!=POStatus.DONE){
						message = new StringBuilder();
						message.append("Erro ao fechar o pedido.");
						message.append(" A OS ");
 						code = formatter.format(productionOrder.getPoNumber());
						message.append(code);
						message.append(" encontra-se em \"");
						message.append(productionOrder.getStatus().getStatusValue());
						message.append("\"");
						message.append(".");
						throw new POSystemException(message.toString());
					}
				}
				request.setStatus(OrderStatus.CLOSED);
				request.setCloseDate(new Date());
				request = executor.executeCommand(new UpdateOrderCommand(request));
				RequestViewBean viewBean = ViewBeanHelper.getRequestHelper().convertRequestToViewBean(request);
				response.setViewBeanResponse(viewBean);
				response.setMessage("Pedido fechado");
				response.setStatus(ServiceResponseEnum.OK.ordinal());
				logger.debug("Pedido fechado.");
			}catch (NumberFormatException exc) {
				logger.error(exc);
				response.setMessage("Parametro informado � invalido");
				response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			}catch(POSystemException exc) {
				logger.error(exc);
				response.setMessage(exc.getMessage());
				response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			}
		}
		return response;
	}
	@PUT
	@Path("/cancel/{id}")
	@Produces("application/json")
	@BadgerFish
	public RequestServiceResponse cancelOrder(@PathParam("id") String id){
		RequestServiceResponse response = new RequestServiceResponse();
		if (id==null || id.equals("")){
			response.setMessage("Parametro informado � invalido");
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} else {
			//Checar se � um valor valido
			try {
				Long requestId = Long.parseLong(id);
				HibernateExecutor<Order> executor = new HibernateExecutor<Order>();
				Filter filter = new Filter();
				filter.setClazz(Order.class);
				filter.addArgument(FilterConditions.EQUALS, "id", requestId);
				Order request = executor.executeCommand(new GetOrderCommand(filter));
				request.setStatus(OrderStatus.CANCELLED);
				request = executor.executeCommand(new UpdateOrderCommand(request));
				RequestViewBean viewBean = ViewBeanHelper.getRequestHelper().convertRequestToViewBean(request);
				response.setViewBeanResponse(viewBean);
				response.setStatus(ServiceResponseEnum.OK.ordinal());
				logger.debug("Pedido cancelado.");
				response.setMessage("Pedido cancelado");
			}catch (NumberFormatException exc) {
				logger.error(exc);
				response.setMessage("Parametro informado � invalido");
				response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			}
		}
		return response;
	}
	@GET
	@Path("/get/openordersbycustomer/{id}")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	public RequestViewBean[] getOpenOrdersByCustomer(@PathParam("id") String id) {
		if (id==null || id.equals("")){
			throw new POSystemException("Informe o id do cliente.");
		} else {
			//Checar se � um valor valido
			try {
				Integer customerId = Integer.parseInt(id);
				HibernateExecutor<List<Order>> executor = new HibernateExecutor<List<Order>>();
				Filter filter = new Filter();
				filter.setClazz(Order.class);
				filter.addArgument(FilterConditions.EQUALS, "customer.id", customerId);
				
				
				filter.addArgument(FilterConditions.IN, "status",
						new OrderStatus[] { OrderStatus.OPEN,
								OrderStatus.EXECUTING });
				List<Order> results = executor.executeCommand(new FilterOrderCommand(filter));
				RequestListViewBean viewBeanList = ViewBeanHelper.getRequestHelper().convertRequestListToViewBeanList(results);
				return viewBeanList.getItems();
			}catch (NumberFormatException exc) {
				logger.error(exc);
				throw new POSystemException(exc);
			}
		}
	}
	@GET
	@Path("/get/ordersbycustomer/{id}")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	public RequestViewBean[] getOrdersByCustomer(@PathParam("id") String id) {
		if (id==null || id.equals("")){
			throw new POSystemException("Informe o id do cliente.");
		} else {
			//Checar se � um valor valido
			try {
				Integer customerId = Integer.parseInt(id);
				HibernateExecutor<List<Order>> executor = new HibernateExecutor<List<Order>>();
				Filter filter = new Filter();
				filter.setClazz(Order.class);
				filter.addArgument(FilterConditions.EQUALS, "customer.id", customerId);
				List<Order> results = executor.executeCommand(new FilterOrderCommand(filter));
				RequestListViewBean viewBeanList = ViewBeanHelper.getRequestHelper().convertRequestListToViewBeanList(results);
				return viewBeanList.getItems();
			}catch (NumberFormatException exc) {
				logger.error(exc);
				throw new POSystemException(exc);
			}
		}
	}
	@GET
	@Path("/get/ordersdashboardvalues/{fromDate}&{toDate}")
	@Produces("application/json")
	@BadgerFish
	public RequestDashboardViewBean getOrderDashboardValues(@PathParam("fromDate") String fromDate, @PathParam("toDate") String toDate) {
		String dashboardDatePattern = SystemMessages.getMessage("dashboard_date_pattern");
		DateFormat df = new SimpleDateFormat(dashboardDatePattern);

		String startDateStr = StringUtils.replace(fromDate, "-", "/");
		String endDateStr = StringUtils.replace(toDate, "-", "/");
		
		try {
		
			Date startDate = df.parse(startDateStr);
			Date endDate = df.parse(endDateStr);

			df = new SimpleDateFormat(SystemMessages.getMessage("filter_date_pattern"));
			
			Filter openReqFilter = new Filter();
			openReqFilter.setClazz(Order.class);
			openReqFilter.addArgument(FilterConditions.EQUALS, "status", OrderStatus.OPEN);
			openReqFilter.addArgument(FilterConditions.BETWEEN, "openDate",
					new Date[] { startDate, endDate });
			
			Filter execReqFilter = new Filter();
			execReqFilter.setClazz(Order.class);
			execReqFilter.addArgument(FilterConditions.EQUALS, "status", OrderStatus.EXECUTING);
			execReqFilter.addArgument(FilterConditions.BETWEEN, "openDate",
					new Date[] { startDate, endDate });

			
			Filter billReqFilter = new Filter();
			billReqFilter.setClazz(Order.class);
			billReqFilter.addArgument(FilterConditions.EQUALS, "status", OrderStatus.BILLED);
			billReqFilter.addArgument(FilterConditions.BETWEEN, "openDate",
					new Date[] { startDate, endDate });
			
			Filter closedReqFilter = new Filter();
			closedReqFilter.setClazz(Order.class);
			closedReqFilter.addArgument(FilterConditions.EQUALS, "status", OrderStatus.CLOSED);
			closedReqFilter.addArgument(FilterConditions.BETWEEN, "openDate",
					new Date[] { startDate, endDate });
			
			//Executar os filtros
			HibernateExecutor<List<Order>> executor = new HibernateExecutor<List<Order>>();
			
			List<Order> openReqResults = executor.executeCommand(new FilterOrderCommand(openReqFilter));
			List<Order> exeReqResults = executor.executeCommand(new FilterOrderCommand(execReqFilter));
			List<Order> billReqResults = executor.executeCommand(new FilterOrderCommand(billReqFilter));
			List<Order> closedReqResults = executor.executeCommand(new FilterOrderCommand(closedReqFilter));
			
			
			RequestDashboardViewBean dashboard = new RequestDashboardViewBean();
			dashboard.getBilled().setValue(String.valueOf(billReqResults.size()));
			dashboard.getExecuting().setValue(String.valueOf(exeReqResults.size()));
			dashboard.getOpen().setValue(String.valueOf(openReqResults.size()));
			dashboard.getClosed().setValue(String.valueOf(closedReqResults.size()));
			
			return dashboard;
		} catch (ParseException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		}
	}
	
	/**
	 * Realiza o calculo dos valores a serem pagos no faturamento,
	 * de acordo com o pedido e o tipo de pagamento fornecidos.<br/>
	 * O tipo de pagamento pode ser  v=&agrave; vista e p=parcelado. <br/>
	 * Ver os fields RequestService.PAYMENT_TYPE_INSTALLMENTS e 
	 * RequestService.PAYMENT_TYPE_NO_INSTALLMENTS
	 * 
	 * @param reqId id do pedido
	 * @param paymentType tipo de pagamento
	 * @param qtd quantide de parcelas
	 * @return objeto contendo os valores e as datas de pagamento
	 */
	@GET
	@Path("/get/billingvalues/{reqId}&{paymentType}&{qtd}")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	public ProcessBillingValuesServiceResponse calculateBillingValues(@PathParam("reqId")  String reqId
			,@PathParam("paymentType") String paymentType,@PathParam("qtd") String qtd) {

		ProcessBillingValuesServiceResponse response = new ProcessBillingValuesServiceResponse();
		try {
			OrderService orderService = new OrderService();
			Order request = orderService.getOrderById(reqId);
			List<ProcessBillingValuesViewBean> values = 
						orderService.calculateBillingValues(request, paymentType, Integer.parseInt(qtd));
			JSONObject jsonResponse = ViewBeanHelper.getJSONViewHelper().convertBeanListToJSONGridItems(values);

			response.setStatus(ServiceResponseEnum.OK.ordinal());
			response.setMessage("Dados processados com sucesso");
			response.setGridValue(jsonResponse.toString());
			
		} catch (POSystemException exc) {
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getSystemMessages());
		} catch (Exception exc) {
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getMessage());
		}
		return response;
	}
	@POST
	@Path("/processbilling")
	@Produces("application/json")
	@Consumes("application/json")
	@BadgerFish
	public ServiceResponse processBilling(ProcessBillingViewBean viewBean) {
		
		ServiceResponse serviceResponse = new ServiceResponse();
		try {
			OrderService orderService = new OrderService();
			//Recuperar o pedido a partir do view bean
			Order order = orderService.getOrderById(viewBean.getOrderId());
			//Processar faturamento do pedido
			Order updatedOrder = orderService.processBilling(order, viewBean.getPaymentType(), viewBean.getValues());
			serviceResponse.setStatus(ServiceResponseEnum.OK.ordinal());
			StringBuilder message = new StringBuilder();
			message.append("O faturamento do pedido ");
			message.append(updatedOrder.getOrderCode());
			message.append(" foi processado com sucesso.");
			serviceResponse.setMessage(message.toString());
			
		} catch (POSystemException exc) {
			serviceResponse.setMessage(exc.getSystemMessages());
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} catch (Exception exc) {
			serviceResponse.setMessage(exc.getMessage());
			serviceResponse.setStatus(ServiceResponseEnum.FAILED.ordinal());
		}
		
		return serviceResponse;
	}
	@GET
	@Path("/get/billedordersbycustomer/{id}")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	public RequestViewBean[] getBilledRequestsByCustomer(@PathParam("id") String id) {
		if (id==null || id.equals("")){
			throw new POSystemException("Informe o id do cliente.");
		} else {
			//Checar se � um valor valido
			try {
				Long customerId = Long.parseLong(id);
				HibernateExecutor<List<Order>> executor = new HibernateExecutor<List<Order>>();
				Filter filter = new Filter();
				filter.setClazz(Order.class);
				filter.addArgument(FilterConditions.EQUALS, "customer", customerId);
				
				
				filter.addArgument(FilterConditions.EQUALS, "status",
						OrderStatus.BILLED);
				List<Order> results = executor.executeCommand(new FilterOrderCommand(filter));
				RequestListViewBean viewBeanList = ViewBeanHelper.getRequestHelper().convertRequestListToViewBeanList(results);
				return viewBeanList.getItems();
			}catch (NumberFormatException exc) {
				logger.error(exc);
				throw new POSystemException(exc);
			}
		}
	}	
	
	
}

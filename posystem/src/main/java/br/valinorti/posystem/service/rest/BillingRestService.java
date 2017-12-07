/**
 * 
 */
package br.valinorti.posystem.service.rest;

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
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.exception.POSystemException;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.ExecuteFilterCommand;
import br.valinorti.posystem.command.GetBillingByIdCommand;
import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.entity.BillingStatus;
import br.valinorti.posystem.service.BaseRESTService;
import br.valinorti.posystem.service.billing.view.BillingFilterViewBean;
import br.valinorti.posystem.service.billing.view.BillingServiceResponse;
import br.valinorti.posystem.service.billing.view.BillingViewBean;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;
import br.valinorti.util.POSystemUtils;

/**
 * Classe que prove servicos REST de faturamento.
 * 
 * @author leafar
 *
 */
@Path("/{subscriber}/billing")
public class BillingRestService extends BaseRESTService {

	private static Logger logger = Logger.getLogger(BillingRestService.class); 
	
	@POST
	@Path("filter")
	@Consumes("application/json")
	@Produces("text/plain")
	@GZIP
	@BadgerFish
	public String filter(BillingFilterViewBean viewBean) {
		try {
			Filter filter = new Filter();
			filter.setClazz(Billing.class);
			if (StringUtils.isNotBlank(viewBean.getCustomerId())) {
				filter.addArgument(FilterConditions.EQUALS, "order.customer.id", Integer.parseInt(viewBean.getCustomerId()));				
			}
			
			if (viewBean.getBilledDateRange()!=null && viewBean.getBilledDateRange().length==2) {
				Date fromDate = POSystemUtils.parseDate(viewBean.getBilledDateRange()[0]);
				Date toDate = POSystemUtils.parseDate(viewBean.getBilledDateRange()[1]);
				
				filter.addArgument(FilterConditions.BETWEEN, "date",
						new Date[] { fromDate, toDate });
			}
			if (StringUtils.isNotBlank(viewBean.getStatus())) {
				BillingStatus status = BillingStatus.values()[Integer.parseInt(viewBean.getStatus())];
				filter.addArgument(FilterConditions.EQUALS, "status", status);
			}
			
			HibernateExecutor<List<Billing>> executor = new HibernateExecutor<List<Billing>>();
			List<Billing> results = executor.executeCommand(new ExecuteFilterCommand<Billing>(filter));

			List<BillingViewBean> viewBeanList = ViewBeanHelper.getBillingViewBeanHelper().convertEntityListToViewList(results);
			JSONObject jsonResponse = ViewBeanHelper.getJSONViewHelper()
							.convertBeanListToJSONGridItems(viewBeanList);
			
			logger.debug("Faturas filtradas:" + viewBeanList.size());
			return jsonResponse.toString();
		} catch (POSystemException exc) {
			logger.error(exc.getSystemMessages());
			throw exc;
		} catch (Exception exc) {
			logger.error(exc.getMessage());
			throw new POSystemException(exc);
		}
	}
	
	@GET
	@Path("/get/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	public BillingServiceResponse getById(@PathParam("id") String billId) {
		BillingServiceResponse response = new BillingServiceResponse();
		
		if (StringUtils.isBlank(billId) || !StringUtils.isNumeric(billId)) {
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage("Id do faturamento &eacute; inv&aacute;lido.");
			return response;
		}
		
		try {
			Long id = Long.parseLong(billId);
			HibernateExecutor<Billing> executor = new HibernateExecutor<Billing>();
			Billing billing = executor.executeCommand(new GetBillingByIdCommand(id));
			
			BillingViewBean billingViewBean = ViewBeanHelper.getBillingViewBeanHelper().convertEntityToViewBean(billing);
			
			response.setStatus(ServiceResponseEnum.OK.ordinal());
			response.setViewBean(billingViewBean);
		} catch (POSystemException exc) {
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getSystemMessages());
		} catch (Exception exc) {
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			response.setMessage(exc.getMessage());
		}
		return response;
	}
	@GET
	@Path("/get/report/totalbyperiod/{from}/{to}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	public String getReportBillingTotalByPeriod(@PathParam("from")String from,@PathParam("to") String to) {
		if (StringUtils.isBlank(from) || StringUtils.isBlank(to)) {
			return null;
		}
		try {
			String startDateStr = StringUtils.replace(from, "-", "/");
			String endDateStr = StringUtils.replace(to, "-", "/");			
			
			Date fromDate = POSystemUtils.parseDate(startDateStr);
			Date toDate = POSystemUtils.parseDate(endDateStr);
			
			//Montar filtro
			Filter filter = new Filter();
			filter.setClazz(Billing.class);
			
			filter.addArgument(FilterConditions.BETWEEN, "date", new Date[] {
					fromDate, toDate });
			
			filter.addArgument(FilterConditions.EQUALS, "status", BillingStatus.CLOSED);
			
			HibernateExecutor<List<Billing>> executor = new HibernateExecutor<List<Billing>>();
			List<Billing> results = executor.executeCommand(new ExecuteFilterCommand<Billing>(filter));
			
			List<BillingViewBean> viewBeanList = ViewBeanHelper.getBillingViewBeanHelper().convertEntityListToViewList(results);
			
			JSONObject jsonResponse = ViewBeanHelper.getJSONViewHelper().convertBeanListToJSONGridItems(viewBeanList);

			return jsonResponse.toString();
		} catch (POSystemException exc) {
			logger.error(exc);
		} catch (Exception exc) {
			logger.error(exc);
		}
		return null;
	}
	
	
	
}

/**
 * 
 */
package br.valinorti.posystem.service.rest;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.commons.lang.StringUtils;
import org.jboss.resteasy.annotations.GZIP;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import br.valinorti.exception.POSystemException;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.Filter.OrderByType;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.ExecuteFilterCommand;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;
import br.valinorti.posystem.service.BaseRESTService;
import br.valinorti.posystem.service.InstallmentService;
import br.valinorti.posystem.service.installment.view.InstallmentDashboardResponse;
import br.valinorti.posystem.service.installment.view.InstallmentDashboardViewBean;
import br.valinorti.posystem.service.installment.view.InstallmentViewBean;
import br.valinorti.posystem.service.installment.view.InstallmentsByDaysViewBean;
import br.valinorti.posystem.service.installment.view.PayIntallmentViewBean;
import br.valinorti.posystem.service.installment.view.RetrievePendingInstallmentsByDaysResponse;
import br.valinorti.posystem.service.view.ServiceResponse;
import br.valinorti.posystem.service.view.ServiceResponseEnum;
import br.valinorti.util.POSystemUtils;

/**
 * @author rchiari
 *
 */
@Path("/{subscriber}/installments")
public class InstallmentRestService extends BaseRESTService {
	/**
	 * Recupera 
	 * 
	 * @param days
	 * @return
	 */
	@GET
	@Path("/pendinginstallments/by/{days}/days")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish	
	public RetrievePendingInstallmentsByDaysResponse retrievePendingInstallments(@PathParam("days")String days) {
		RetrievePendingInstallmentsByDaysResponse response = new RetrievePendingInstallmentsByDaysResponse();
		if (StringUtils.isBlank(days) || !StringUtils.isNumeric(days)) {
			response.setMessage("Param&acirc;tro inv&aacute;lido.");
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			return response;
		}
		try {
			InstallmentService installmentService = new InstallmentService();
			List<Installment> installments = installmentService.retrievePendingInstallments(Integer.parseInt(days));
			//Transformar as entidades em viewbean
			List<InstallmentsByDaysViewBean> viewBeanList = new ArrayList<InstallmentsByDaysViewBean>();
			String instDate = "";
			String instCustomer = "";
			String instValue = "";
			String instId = "";
			for (Installment installment : installments) {
				instId = installment.getId().toString();
				instDate = POSystemUtils.formatDate(installment.getDate());
				instCustomer = installment.getBilling().getOrder().getCustomer().getName();
				instValue = POSystemUtils.getCurrencyValueAsString(installment.getValue());
				viewBeanList.add(new InstallmentsByDaysViewBean(instId,instDate,instCustomer,instValue));
			}
			response.setInstallments(viewBeanList);
			response.setStatus(ServiceResponseEnum.OK.ordinal());
			response.setMessage("Parcelas recuperadas com sucesso.");
		} catch (POSystemException exc) {
			response.setMessage(exc.getSystemMessages());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} catch (Exception exc) {
			response.setMessage(exc.getMessage());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());			
		}
		
		return response;
	}
	@GET
	@Path("/get/{id}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish	
	public InstallmentDashboardResponse getInstallmentById(@PathParam("id") String id) {
		InstallmentDashboardResponse response = new InstallmentDashboardResponse();
		if (StringUtils.isBlank(id) || !StringUtils.isNumeric(id)) {
			response.setMessage("Param&acirc;tro inv&aacute;lido.");
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
			return response;
		}
		try {
			InstallmentService service = new InstallmentService();
			Installment installment = service.getById(Long.parseLong(id));
			InstallmentDashboardViewBean viewbean = new InstallmentDashboardViewBean();
			viewbean.setCustomerName(installment.getBilling().getOrder().getCustomer().getName());
			viewbean.setDate(POSystemUtils.formatDate(installment.getDate()));
			viewbean.setOrderCode(installment.getBilling().getOrder().getOrderCode());
			viewbean.setOrderFinalPrice(
					POSystemUtils.getCurrencyValueAsString(installment.getBilling().getFinalValue()));
			viewbean.setStatus(installment.getStatus().getStatusValue());
			viewbean.setValue(POSystemUtils.getCurrencyValueAsString(installment.getValue()));
			viewbean.setInstallmentId(installment.getId().toString());
			response.setInstallment(viewbean);
			response.setStatus(ServiceResponseEnum.OK.ordinal());
		} catch (POSystemException exc) {
			response.setMessage(exc.getSystemMessages());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} catch (Exception exc) {
			response.setMessage(exc.getMessage());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());			
		}
		
		return response;
	}
	@GET
	@Path("/get/by/billingid/{billingId}")
	@Consumes("text/plain")
	@Produces("application/json")
	@BadgerFish
	@GZIP
	public InstallmentViewBean[] getInstallmentsByBilling(@PathParam("billingId") String billId) {
		if (StringUtils.isBlank(billId) || !StringUtils.isNumeric(billId)) {
			return null;
		}
		Filter filter = new Filter();
		filter.setClazz(Installment.class);
		filter.addArgument(FilterConditions.EQUALS, "billing.id", Long.parseLong(billId));
		filter.addOrderBy(new String[]{"date"}, OrderByType.ASCENDING);
		
		HibernateExecutor<List<Installment>> executor = new HibernateExecutor<List<Installment>>();
		List<Installment> results = executor.executeCommand(new ExecuteFilterCommand<Installment>(filter));
		
		InstallmentViewBean[] viewBeans = new InstallmentViewBean[results.size()];
		
		int cont = 0;
		InstallmentViewBean viewBean = null;
		for (Installment installment : results) {
			viewBean = new InstallmentViewBean();
			viewBean.setDate(POSystemUtils.formatDate(installment.getDate()));
			viewBean.setStatusIntValue(String.valueOf(installment.getStatus().ordinal()));
			viewBean.setValue(POSystemUtils.getCurrencyValueAsString(installment.getValue()));
			viewBean.setId(installment.getId().toString());
			viewBeans[cont] = viewBean;
			cont++;
		}
		return viewBeans;
	}
	@PUT
	@Path("/pay")
	@Produces("application/json")
	@Consumes("application/json")
	@BadgerFish
	public ServiceResponse payInstallment(PayIntallmentViewBean viewBean) {
		ServiceResponse response = new ServiceResponse();
		try {
			InstallmentService service = new InstallmentService();
			Installment installment = service.getById(Long.parseLong(viewBean.getInstallmentId()));
			installment.setFinalValue(POSystemUtils.getCurrencyValue(viewBean.getPaidValue()).doubleValue());
			installment.setStatus(InstallmentStatus.PAID);
			Date today = new Date();
			
			installment.setPaymentDate(today);
			
			service.payInstallment(installment);
			response.setStatus(ServiceResponseEnum.OK.ordinal());

		} catch (POSystemException exc) {
			response.setMessage(exc.getSystemMessages());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} catch (ParseException exc) {
			response.setMessage("Por favor, informe o valor da parcela no formato R$ 9999,99. Exemplo: R$ 450,30");
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		} catch (Exception exc) {
			response.setMessage(exc.getMessage());
			response.setStatus(ServiceResponseEnum.FAILED.ordinal());
		}
		return response;
	}
	
}

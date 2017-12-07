/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.valinorti.exception.POSystemException;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.POStatus;
import br.valinorti.posystem.entity.ProductionOrder;
import br.valinorti.posystem.service.po.view.POFilterViewBean;
import br.valinorti.posystem.service.po.view.POViewBean;
import br.valinorti.util.SystemMessages;

/**
 * @author Rafael Chiarinelli
 *
 */
public class POViewHelper {
	
	private static Logger logger = Logger.getLogger(POViewHelper.class);
	
	/**
	 * 
	 * @param viewBeanList
	 * @return
	 */
	public synchronized List<ProductionOrder> convertViewBeanToPOList(List<POViewBean> viewBeanList) {
		List<ProductionOrder> poList = new ArrayList<ProductionOrder>();
		for (POViewBean viewBean : viewBeanList) {
			poList.add(this.convertViewBeanToPO(viewBean));
		}
		return poList;
	}
	
	/**
	 * 
	 * @param viewBean
	 * @return
	 */
	public synchronized ProductionOrder convertViewBeanToPO(POViewBean viewBean) {
		ProductionOrder po = new ProductionOrder();
		String datePattern = SystemMessages.getMessage("date_pattern");
		DateFormat df = new SimpleDateFormat(datePattern);
		List<String> messages = new ArrayList<String>();
		try {
			if (!StringUtils.isBlank(viewBean.getDeliverDate())) {
				po.setDeliverDate(df.parse(viewBean.getDeliverDate()));
			}
		} catch(ParseException pe) {
			logger.error(pe);
			messages.add("Data de fechamento � inv�lida");
		}
		try {
			if (!StringUtils.isBlank(viewBean.getDueDate())) {
				po.setDueDate(df.parse(viewBean.getDueDate()));
			}
		} catch (ParseException pe) {
			logger.error(pe);
			messages.add("Data de entrega � inv�lida");
		}
		if (!StringUtils.isBlank(viewBean.getEstimatedTime())) {
			try {
				po.setEstimatedTime(Double.parseDouble(viewBean.getEstimatedTime()));
			} catch(NumberFormatException nfe) {
				logger.error(nfe);
				messages.add("Tempo estimado � inv�lido. Ex: 20.5");
			}
		}
		if (!StringUtils.isBlank(viewBean.getPoId())){
			try {
				po.setId(Long.parseLong(viewBean.getPoId()));
			} catch(NumberFormatException nfe) {
				logger.error(nfe);
				messages.add("Id informado � invalido.");
			}
		}
		if (!StringUtils.isBlank(viewBean.getPoNumber())) {
			try {
				po.setPoNumber(Integer.parseInt(viewBean.getPoNumber()));
			} catch(NumberFormatException nfe) {
				logger.error(nfe);
				messages.add("N�mero da ordem de servi�o � inv�lida.");
			}
		}
		if (!StringUtils.isBlank(viewBean.getQtd())){
			try {
				po.setQtd(Integer.parseInt(viewBean.getQtd()));
			} catch(NumberFormatException nfe) {
				logger.error(nfe);
				messages.add("Quantidade informada � inv�lida.");
			}
		}
		if (!StringUtils.isBlank(viewBean.getRequest())) {
			try {
				Order request = new Order();
				request.setId(Long.parseLong(viewBean.getRequest()));
				po.setOrder(request);
			} catch(NumberFormatException nfe) {
				logger.error(nfe);
				messages.add("Id do pedido � invalido.");
			}
		}
		if (!StringUtils.isBlank(viewBean.getSketchNumber())) {
			po.setSketchNumber(viewBean.getSketchNumber());
		}
		
		if (!StringUtils.isBlank(viewBean.getStatus())) {
			po.setStatus(POStatus.valueOf(viewBean.getStatus()));
		}
		if (!StringUtils.isBlank(viewBean.getStatusId())) {
			POStatus[] status = POStatus.values();
			for (POStatus poStatus : status) {
				if(poStatus.ordinal()==Integer.parseInt(viewBean.getStatusId())) {
					po.setStatus(poStatus);
					break;
				}
			}
		}
		if (!StringUtils.isBlank(viewBean.getOpenDate())) {
			try {
				po.setOpenDate(df.parse(viewBean.getOpenDate()));
			} catch(ParseException exc) {
				logger.error(exc);
				messages.add("Data de abertura � obrigat�ria.");
			}
		}
		
		//Caso n�o estiver vazio, lan�ar exce��o
		if (!messages.isEmpty()) {
			throw new POSystemException(messages);
		}
		return po;
	}
	
	/**
	 * 
	 * @param filterViewBean
	 * @return
	 */
	public synchronized Filter convertFilterViewBeanToFilter(POFilterViewBean filterViewBean){
		Filter filter = new Filter();
		filter.setClazz(ProductionOrder.class);
		List<String> messages = new ArrayList<String>();

		//Tratar periodo informado
		try {
			if (filterViewBean.getOpenDateRange()!=null 
					&& filterViewBean.getOpenDateRange().length > 0
					&& filterViewBean.getOpenDateRange().length == 2){
				
				String datePattern = SystemMessages.getMessage("date_pattern");
		
				SimpleDateFormat df = new SimpleDateFormat(datePattern);
				df.setLenient(false);

				Date startDate = df.parse(filterViewBean.getOpenDateRange()[0]);
				Date endDate = df.parse(filterViewBean.getOpenDateRange()[1]);
				
				filter.addArgument(FilterConditions.BETWEEN, "dueDate", new Date[]{startDate,endDate});
			}
		} catch(ParseException pe){
			logger.error(pe);
			String lineSeparator = SystemMessages.getMessage("response_line_break");
			messages.add("Erro na formata&ccedil;&atilde;o das datas informadas.");
			messages.add(lineSeparator);
		}
		//Tratar se os codigos forem informados
		if (filterViewBean.getCodes()!=null 
				&& !filterViewBean.getCodes().equals("")){
			String[] poCodes = filterViewBean.getCodes().split(",");
			filter.addArgument(FilterConditions.IN, "poNumber", poCodes);
		}
		
		//Status
		if (filterViewBean.getStatus()!=null 
				&& !filterViewBean.getStatus().equals("")){
			POStatus status = POStatus.values()[Integer.parseInt(filterViewBean.getStatus())];
			filter.addArgument(FilterConditions.EQUALS, "status", status);
		}
		//Requests
		if (filterViewBean.getRequests()!=null
				&& filterViewBean.getRequests().length>0) {
			String[] reqs = filterViewBean.getRequests();
			Long[] reqIds = new Long[reqs.length];
			for (int i = 0; i < reqs.length; i++) {
				reqIds[i] = Long.parseLong(reqs[i]);
			}
			
			filter.addArgument(FilterConditions.IN, "order.id", reqIds);
		}
		
		if (!messages.isEmpty()){
			throw new POSystemException(messages);
		}
		
		return filter;
	}
	
	/**
	 * 
	 * @param po
	 * @return
	 */
	public synchronized POViewBean convertPOToViewBean(ProductionOrder po) {
		POViewBean viewBean = new POViewBean();
		
		String datePattern = SystemMessages.getMessage("date_pattern");
		DateFormat df = new SimpleDateFormat(datePattern);
		
		if (po.getDeliverDate()!=null){
			viewBean.setDeliverDate(df.format(po.getDeliverDate()));
		}
		
		if (po.getDueDate()!=null){
			viewBean.setDueDate(df.format(po.getDueDate()));
		}
		
		if (po.getEstimatedTime()!=null){
			viewBean.setEstimatedTime(po.getEstimatedTime().toString());
		}
		
		viewBean.setPoId(po.getId().toString());
		
		NumberFormat formatter = new DecimalFormat("000000"); 
		String code = formatter.format(po.getPoNumber());
		
		viewBean.setPoNumber(code);
		
		viewBean.setQtd(po.getQtd().toString());

		if (po.getOrder()!=null){
			viewBean.setRequest(po.getOrder().getOrderCode());
		}
		
		if (po.getSketchNumber()!=null){
			viewBean.setSketchNumber(po.getSketchNumber());
		}
		
		viewBean.setStatus(po.getStatus().getStatusValue());
		viewBean.setStatusId(String.valueOf(po.getStatus().ordinal()));
		viewBean.setCustomer(po.getOrder().getCustomer().getName());
		viewBean.setCustomerId(po.getOrder().getCustomer().getId().toString());
		viewBean.setRequestId(po.getOrder().getId().toString());
		viewBean.setOpenDate(df.format(po.getOpenDate()));	
		
		return viewBean;
	}
	/**
	 * 
	 * @param list
	 * @return
	 */
	public synchronized List<POViewBean> convertPOListToViewBeanList(List<ProductionOrder> list){
		List<POViewBean> viewBeanList = new ArrayList<POViewBean>();
		for (ProductionOrder po : list) {
			viewBeanList.add(this.convertPOToViewBean(po));
		}
		return viewBeanList;
	}
	
}

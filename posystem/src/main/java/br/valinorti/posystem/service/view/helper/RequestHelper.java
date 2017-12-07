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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.valinorti.exception.POSystemException;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.service.order.view.AddRequestViewBean;
import br.valinorti.posystem.service.order.view.RequestListViewBean;
import br.valinorti.posystem.service.order.view.RequestViewBean;
import br.valinorti.util.SystemMessages;

/**
 * @author Rafael Chiarinelli
 *
 */
public class RequestHelper {
	
	private static Logger logger = Logger.getLogger(RequestHelper.class);
	
	/**
	 * 
	 * @param viewBean
	 * @return
	 */
	public synchronized Order convertViewBeanToRequest(RequestViewBean viewBean) {
		Order request = new Order();
		List<String> messages = new ArrayList<String>();
		if (viewBean.getCustomer()!=null && !viewBean.getCustomer().equals("")) {
			//instanciar um Customer independente do tipo
			Customer customer = new PFCustomer();
			customer.setId(Integer.parseInt(viewBean.getCustomer()));
			request.setCustomer(customer);
		}
		if (viewBean.getDescription()!=null && !viewBean.getDescription().equals("")) {
			request.setDescription(viewBean.getDescription());
		}
		request.setOpenDate(new Date());
		if (viewBean.getCode()!=null && !viewBean.getCode().equals("")) {
			request.setOrderCode(viewBean.getCode());
		}
		if (viewBean.getPrice()!=null && !viewBean.getPrice().equals("")){
			try {
				String currencyPattern = SystemMessages.getMessage("currency_pattern");
				NumberFormat nf = new DecimalFormat(currencyPattern);
				request.setPrice(nf.parse(viewBean.getPrice()).doubleValue());
			}catch (NumberFormatException nfe){
				logger.error(nfe);
				messages.add("Pre�o informado � inv�lido.Ex:99,99");
			}catch(ParseException pe){
				logger.error(pe);
				messages.add("Pre�o informado � inv�lido.Ex:99,99");
			}
		}
		if (viewBean.getQtd()!=null && !viewBean.getQtd().equals("")){
			try {
				request.setQuantity(Integer.parseInt(viewBean.getQtd()));
			} catch (NumberFormatException nfe){
				logger.error(nfe);
				messages.add("Formato da quantidade informada � inv�lido.");
			}
		}
		if (viewBean.getStatus()!=null && !viewBean.getStatus().equals("")){
			OrderStatus[] values = OrderStatus.values();
			for (int i = 0; i < values.length; i++) {
				if (values[i].getStatusValue().equals(viewBean.getStatus())){
					request.setStatus(values[i]);
				}
			}
		}
		try {
			if (viewBean.getRequestId()!=null && !viewBean.getRequestId().equals("")){
				request.setId(Long.parseLong(viewBean.getRequestId()));
			}
		} catch (NumberFormatException nfe){
			logger.error(nfe);
			messages.add("Formato do id invalido.");
		}
		if (viewBean.getDeliverDate()!=null && !viewBean.getDeliverDate().equals("")){
			try {
				String datePattern = SystemMessages.getMessage("date_pattern");
				DateFormat df = new SimpleDateFormat(datePattern);
				request.setDeliverDate(df.parse(viewBean.getDeliverDate()));
			} catch (ParseException e) {
				messages.add("Data de entrega informada est� incorreta.");
			}
		}
		
		//Caso n�o estiver vazio, lan�ar exce��o
		if (!messages.isEmpty()) {
			throw new POSystemException(messages);
		}
		
		return request;
	}
	
	/**
	 * Esse m�todo gera a partir do valor passado,
	 * o c�digo do pedido,no formato value/ano.
	 * 
	 * @param value
	 * @return o codido no formato 
	 */
	public synchronized String buildCode(int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int year = calendar.get(Calendar.YEAR);
		StringBuilder sb = new StringBuilder();
		sb.append(++value);
		sb.append("/");
		sb.append(year);
		return sb.toString();
	}
	/**
	 * 
	 * @param request
	 * @return
	 */
	public synchronized RequestViewBean convertRequestToViewBean(Order request){
		AddRequestViewBean viewBean = new AddRequestViewBean();
		if (request.getOrderCode()!=null && !request.getOrderCode().equals("")) {
			viewBean.setCode(request.getOrderCode());
		}
		if (request.getCustomer()!=null) {
			viewBean.setCustomer(request.getCustomer().getName());
		}
		if (request.getDescription()!=null && !request.getDescription().equals("")) {
			viewBean.setDescription(request.getDescription());
		}
		if (request.getPrice()!=null && request.getPrice()>0) {
			String decimalFormat = SystemMessages.getMessage("currency_pattern_out");
			NumberFormat nf = new DecimalFormat(decimalFormat);
			viewBean.setPrice(nf.format(request.getPrice()));
		}
		if (request.getQuantity()!=null && request.getQuantity()>0) {
			viewBean.setQtd(request.getQuantity().toString());
		}
		if (request.getStatus()!=null) {
			viewBean.setStatus(request.getStatus().getStatusValue());
		}
		if (request.getId()!=null && request.getId()>0) {
			viewBean.setRequestId(request.getId().toString());
		}
		if (request.getOpenDate()!=null) {
			String datePattern = SystemMessages.getMessage("date_pattern");
			DateFormat df = new SimpleDateFormat(datePattern);
			viewBean.setOpenDate(df.format(request.getOpenDate()));
		}
		if (request.getDeliverDate()!=null) {
			String datePattern = SystemMessages.getMessage("date_pattern");
			DateFormat df = new SimpleDateFormat(datePattern);
			viewBean.setDeliverDate(df.format(request.getDeliverDate()));
		}
		return viewBean;
	}
	
	/**
	 * 
	 * @param requestList
	 * @return
	 */
	public synchronized RequestListViewBean convertRequestListToViewBeanList(List<Order> requestList){
		RequestViewBean[] viewBeanArray = new RequestViewBean[requestList.size()];
		RequestListViewBean requestListVB = new RequestListViewBean();
		int i = 0;
		for (Order request : requestList) {
			viewBeanArray[i] = this.convertRequestToViewBean(request);
			i++;
		}
		requestListVB.setItems(viewBeanArray);
		return requestListVB;
	}
	/**
	 * 
	 * @param requestList
	 * @return
	 */
	public synchronized List<RequestViewBean> convertRequestListToRequestViewBeanList(List<Order> requestList){
		List<RequestViewBean> viewBeanArrayList = new ArrayList<RequestViewBean>();
		for (Order request : requestList) {
			viewBeanArrayList.add(this.convertRequestToViewBean(request));
		}
		return viewBeanArrayList;
	}	
	
	/**
	 * 
	 * @param viewBean
	 * @return
	 */
	public synchronized JSONObject convertRequestViewBeanToJSON(AddRequestViewBean viewBean) {
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject();
			jsonResponse.put("code",viewBean.getCode());
			jsonResponse.put("customer",viewBean.getCustomer());
			jsonResponse.put("price",viewBean.getPrice());
			jsonResponse.put("qtd",viewBean.getQtd());
			jsonResponse.put("status",viewBean.getStatus());
			jsonResponse.put("requestId",viewBean.getRequestId());
		} catch (JSONException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		}
		return jsonResponse;
	}
	/**
	 * 
	 * @param viewBeanList
	 * @return
	 */
	public synchronized JSONObject convertRequestViewBeanListToJSON(List<AddRequestViewBean> viewBeanList) {
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject entry = null;
			for (AddRequestViewBean requestViewBean : viewBeanList) {
				entry = this.convertRequestViewBeanToJSON(requestViewBean);
				jsonArray.put(entry);
			}
			jsonResponse.put("items", jsonArray);
		} catch (JSONException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		}
		return jsonResponse;
	}
}

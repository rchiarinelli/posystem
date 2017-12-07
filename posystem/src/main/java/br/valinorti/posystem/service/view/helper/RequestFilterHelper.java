/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import br.valinorti.exception.POSystemException;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.service.order.view.RequestFilterViewBean;
import br.valinorti.util.SystemMessages;

/**
 * @author Rafael Chiarinelli
 *
 */
public class RequestFilterHelper {
	/**
	 * Logger de eventos.
	 */
	private static Logger logger = Logger.getLogger(RequestFilterHelper.class);
	
	/**
	 * 
	 * @param filterRequest
	 * @return
	 */
	public synchronized Filter convertFilterRequestToFilter(RequestFilterViewBean filterRequest){
		Filter filter = new Filter();
		filter.setClazz(Order.class);
		List<String> messages = new ArrayList<String>();
		if (filterRequest.getCustomer()!=null && !filterRequest.getCustomer().equals("")){
			filter.addArgument(FilterConditions.EQUALS, "customer.id", Integer.parseInt(filterRequest.getCustomer()));			
		}
		if (filterRequest.getDescription()!=null && !filterRequest.getDescription().equals("")){
			filter.addArgument(FilterConditions.LIKE, "description", "%" + filterRequest.getDescription()+"%");			
		}

		//Tratar periodo informado
		try {
			if (filterRequest.getOpenDateRange()!=null 
					&& filterRequest.getOpenDateRange().length > 0
					&& filterRequest.getOpenDateRange().length == 2){
				
				String datePattern = SystemMessages.getMessage("date_pattern");
		
				SimpleDateFormat df = new SimpleDateFormat(datePattern);
				df.setLenient(false);

				Date startDate = df.parse(filterRequest.getOpenDateRange()[0]);
				Date endDate = df.parse(filterRequest.getOpenDateRange()[1]);
				
				filter.addArgument(FilterConditions.BETWEEN, "openDate", new Date[]{startDate,endDate});
			}
		} catch(ParseException pe){
			logger.error(pe);
			String lineSeparator = SystemMessages.getMessage("response_line_break");
			messages.add("Erro na formata&ccedil;&atilde;o das datas informadas.");
			messages.add(lineSeparator);
		}
		//Tratar se os codigos que forem informados
		if (filterRequest.getOrderCodes()!=null 
				&& !filterRequest.getOrderCodes().equals("")){
			String[] orderCodes = filterRequest.getOrderCodes().split(",");
			filter.addArgument(FilterConditions.IN, "orderCode", orderCodes);
		}
		//Data abertura
		try {
			if (filterRequest.getDeliverDateRange()!=null 
					&& filterRequest.getDeliverDateRange().length > 0
					&& filterRequest.getDeliverDateRange().length == 2){
				
				String datePattern = SystemMessages.getMessage("date_pattern");
		
				SimpleDateFormat df = new SimpleDateFormat(datePattern);
				df.setLenient(false);

				Date startDate = df.parse(filterRequest.getDeliverDateRange()[0]);
				Date endDate = df.parse(filterRequest.getDeliverDateRange()[1]);
				
				filter.addArgument(FilterConditions.BETWEEN, "deliverDate", new Date[]{startDate,endDate});
			}
		} catch(ParseException pe){
			logger.error(pe);
			String lineSeparator = SystemMessages.getMessage("response_line_break");
			messages.add("Erro na formata&ccedil;&atilde;o das datas de entrega informadas.");
			messages.add(lineSeparator);
		}		
		
		//Status
		if (filterRequest.getStatus()!=null 
				&& !filterRequest.getStatus().equals("")){
			OrderStatus status = OrderStatus.values()[Integer.parseInt(filterRequest.getStatus())];
			filter.addArgument(FilterConditions.EQUALS, "status", status);
		}
		if (!messages.isEmpty()){
			throw new POSystemException(messages);
		}
		
		return filter;
	}

}

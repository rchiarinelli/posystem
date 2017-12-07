/**
 * 
 */
package br.valinorti.posystem.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import br.valinorti.exception.POSystemException;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.GetOrderCommand;
import br.valinorti.posystem.command.SaveBillingCommand;
import br.valinorti.posystem.command.UpdateOrderCommand;
import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.entity.BillingStatus;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.entity.PaymentType;
import br.valinorti.posystem.service.order.view.ProcessBillingValuesViewBean;
import br.valinorti.posystem.service.view.helper.ProcessBillingValuesViewBeanHelper;
import br.valinorti.posystem.service.view.helper.ViewBeanHelper;
import br.valinorti.util.POSystemUtils;
import br.valinorti.util.SystemMessages;

/**
 * @author Rafael Chiarinelli
 *
 */
public class OrderService {
	
	public static final String PAYMENT_TYPE_INSTALLMENTS = "p";
	
	public static final String PAYMENT_TYPE_NO_INSTALLMENTS = "v";
	
	/**
	 * 
	 * @return
	 */
	public Order getOrderById(String id) {
		HibernateExecutor<Order> executor = new HibernateExecutor<Order>();
		Filter filter = new Filter();
		filter.setClazz(Order.class);
		filter.addArgument(FilterConditions.EQUALS, "id", Long.parseLong(id));
		Order request = executor.executeCommand(new GetOrderCommand(filter));
		return request;
	}
	
	
	
	/**
	 * Realiza o calculo dos valores a serem pagos no faturamento,
	 * de acordo com o pedido e o tipo de pagamento fornecidos.<br/>
	 * O tipo de pagamento pode ser v=&agrave; vista e p=parcelado.
	 * 
	 * @param pedido para ser faturado
	 * @param paymentType tipo de pagamento
	 * @param qtd quantide de parcelas
	 * @return objeto contendo os valores e as datas de pagamento
	 */
	public List<ProcessBillingValuesViewBean> calculateBillingValues(final Order request, final String paymentType, final int qtd) {
		//Validar se os parametros foram passados corretamente
		if ((paymentType.equals(PAYMENT_TYPE_INSTALLMENTS)) && (qtd==0 || qtd==1)) {
			throw new POSystemException("Para pagamento parcelado, a quantidade de parcelas deve ser maior que 1.");
		} else if ((paymentType.equals(PAYMENT_TYPE_NO_INSTALLMENTS)) && (qtd==0 || qtd>1)) {
			throw new POSystemException("Para pagamento &agrave; vista, a quandidade deve ser 1.");
		}
		//Validar se o pedido esta com o status CLOSED
		if (!request.getStatus().equals(OrderStatus.CLOSED)) {
			throw new POSystemException("O pedido deve estar fechado para poder ser faturado.");
		}
		
		//Calcular o preco
		Double price = request.getPrice();
		BigDecimal priceBD = new BigDecimal(price.doubleValue());
		BigDecimal installmentValueBD = priceBD.divide(new BigDecimal(qtd),new MathContext(5));
		
		String installmentValue = POSystemUtils.getCurrencyValueAsString(installmentValueBD.doubleValue());
		
		//Definir as datas
		int intervalDaysInstallments = SystemMessages.getIntValue("default_interval_days_installments");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(request.getCloseDate());
		
		int cont = 0;
		
		List<ProcessBillingValuesViewBean> values = new ArrayList<ProcessBillingValuesViewBean>();
		
		Date installmentDate = null;
		
		while(cont<qtd) {
			//caso o tipo de pagamento for parcelado
			if (paymentType.equals(PAYMENT_TYPE_INSTALLMENTS)) {
				//Somar o valor de default_interval_days_installments a data
				calendar.add(Calendar.DAY_OF_YEAR, intervalDaysInstallments);				
			}
			installmentDate = calendar.getTime();
			values.add(new ProcessBillingValuesViewBean(
					installmentValue, POSystemUtils.formatDate(installmentDate)));
			cont++;
		}
		
		return values;		
	}
	
	/**
	 * 
	 * @param order
	 * @param paymentType
	 * @param values
	 * @return
	 */
	public Order processBilling(final Order order, final String paymentType, final List<ProcessBillingValuesViewBean> values) {
		//Validar se o pedido esta com o status CLOSED
		if (!order.getStatus().equals(OrderStatus.CLOSED)) {
			throw new POSystemException("O pedido deve estar fechado para ser faturado.");
		}
		//Caso o pagamento for parcelado, verificar se as datas tem os meses em sequencia
		if (StringUtils.equals(paymentType, PAYMENT_TYPE_INSTALLMENTS))	{
			this.checkInstallmentsData(values);
		}
		if (StringUtils.equals(paymentType, PAYMENT_TYPE_NO_INSTALLMENTS) && values.size() > 1){
			StringBuilder sb = new StringBuilder();
			sb.append("Pagamento a vista deve ter somente uma parcela.");
			throw new POSystemException(sb.toString());
		}
		//Gerar a instancia de Billing
		Billing billing = new Billing();
		billing.setDate(new Date());
		billing.setOrder(order);
		if (paymentType.equals(PAYMENT_TYPE_INSTALLMENTS)) {
			billing.setPaymentType(PaymentType.INSTALLMENTS);
		}
		if (paymentType.equals(PAYMENT_TYPE_NO_INSTALLMENTS)) {
			billing.setPaymentType(PaymentType.NO_INTALLMENTS);
		}
		//Gerar as instancias de Installments
		Set<Installment> installments = new HashSet<Installment>();
		ProcessBillingValuesViewBeanHelper helper = ViewBeanHelper.getProcessBillingValuesViewBeanHelper();
		Installment installment = null;
		for (ProcessBillingValuesViewBean bean : values) {
			installment = helper.convertViewBeanToEntity(bean);
			installment.setStatus(InstallmentStatus.PENDING);
			installment.setBilling(billing);
			installment.setSubscriber(order.getSubscriber());
			
			installments.add(installment);
		}
		//Calcular valor final da fatura
		Double finalValue = 0D;
		for (Installment item : installments) {
			finalValue+=item.getValue();
		}
		billing.setFinalValue(finalValue);		
		billing.setInstallments(installments);
		billing.setStatus(BillingStatus.OPEN);
		billing.setSubscriber(order.getSubscriber());
		
		//Salvar fatura
		HibernateExecutor<Billing> executor = new HibernateExecutor<Billing>();
		executor.executeCommand(new SaveBillingCommand(billing));
		
		//Atualizar status do pedido
		HibernateExecutor<Order> orderExecutor = new HibernateExecutor<Order>();
		order.setStatus(OrderStatus.BILLED);
		orderExecutor.executeCommand(new UpdateOrderCommand(order));
		
		return order;
	}
	/**
	 * Verifica se os dados das parcelas do faturamento estão certas.
	 * 
	 * @param values
	 */
	private void checkInstallmentsData(final List<ProcessBillingValuesViewBean> values) {
		Calendar calendar = Calendar.getInstance(POSystemUtils.getSystemLocale());
		calendar.setLenient(false);
		int prevMonth = -1;
		int currMonth = -1;
		int currYear = -1;
		int prevYear = -1;
		Date installmentDate = null;
		for (ProcessBillingValuesViewBean bean : values) {
			try {
				installmentDate = POSystemUtils.parseDate(bean.getDate());						
			} catch (ParseException exc) {
				StringBuilder sb = new StringBuilder();
				sb.append("A data informada ");
				sb.append(bean.getDate());
				sb.append("não está no formato dia/m&ecirc;s/ano.");
				sb.append("Exemplo..: 01/01/2001.");
				throw new POSystemException(sb.toString());
			}
			calendar.setTime(installmentDate);
			currMonth = calendar.get(Calendar.MONTH);
			currYear = calendar.get(Calendar.YEAR);
			//Caso o mes corrente for menor ou igual ao anterior
			//ou caso o mes corrente não for exatamente o proximo do mes anterior
			if (((currMonth <= prevMonth)
					|| (((currMonth-1)!=prevMonth) && (prevMonth!=-1))) && (currYear == prevYear)) {
				//Lancar uma execao
				StringBuilder sb = new StringBuilder();
				sb.append("Por favor, informe das parcelas em sequ&ecirc;ncia.");
				throw new POSystemException(sb.toString());
			}
			//Atualizar o mes anterior com o valor da iteracao atual 
			//Zerar currMonth para a proxima iteracao
			prevMonth = currMonth;
			currMonth = -1;
			//Atualizar a variavel 
			prevYear = currYear;
		}
	}
}

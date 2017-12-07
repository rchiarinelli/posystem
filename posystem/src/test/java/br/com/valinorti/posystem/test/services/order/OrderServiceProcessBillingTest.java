/**
 * 
 */
package br.com.valinorti.posystem.test.services.order;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.exception.POSystemException;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SaveCustomerCommand;
import br.valinorti.posystem.command.SaveOrderCommand;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.service.OrderService;
import br.valinorti.posystem.service.order.view.ProcessBillingValuesViewBean;
import br.valinorti.util.HibernateUtils;

/**
 * @author leafar
 *
 */
public class OrderServiceProcessBillingTest extends BaseJUnitTest {

	/**
	 * 
	 */
	@Test
	public void processBilling_NoInstallments() {
//		//Salvar cliente
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc(String.valueOf(System.currentTimeMillis()));
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setName("MBB");
		customer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		executor.executeCommand(new SaveCustomerCommand(customer));
		
		//Adicionar request
		HibernateExecutor<Order> reqExecutor = new HibernateExecutor<Order>();
		
		//Montar request false
		Order order = new Order();
		order.setCustomer(customer);
		order.setDescription("XXXXX");
		order.setOpenDate(new Date());
		order.setPrice(100.99);
		order.setQuantity(102);
		order.setStatus(OrderStatus.CLOSED);
		order.setCloseDate(new Date());
		order.setOrderCode("ORDER_CODE" + System.currentTimeMillis());
		order.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		Order retOrder = reqExecutor.executeCommand(new SaveOrderCommand(order));
		
		List<ProcessBillingValuesViewBean> values = new ArrayList<ProcessBillingValuesViewBean>();
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/01/2011"));
		OrderService orderService = new OrderService();
		Order updatedOrder = orderService.processBilling(retOrder,OrderService.PAYMENT_TYPE_NO_INSTALLMENTS,values);
		
		Assert.assertNotNull("O servico deve retornar uma resposta.",updatedOrder);
	}
	
	@Test
	public void testProcessBilling_InstallmentDatesWithoutSequence() {
//		//Salvar cliente
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc(String.valueOf(System.currentTimeMillis()));
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setName("MBB");
		customer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		executor.executeCommand(new SaveCustomerCommand(customer));
		
		//Adicionar request
		HibernateExecutor<Order> reqExecutor = new HibernateExecutor<Order>();
		
		//Montar request false
		Order order = new Order();
		order.setCustomer(customer);
		order.setDescription("XXXXX");
		order.setOpenDate(new Date());
		order.setPrice(100.99);
		order.setQuantity(102);
		order.setStatus(OrderStatus.CLOSED);
		order.setCloseDate(new Date());
		order.setOrderCode("ORDER_CODE" + System.currentTimeMillis());
		order.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		Order retOrder = reqExecutor.executeCommand(new SaveOrderCommand(order));
		
		List<ProcessBillingValuesViewBean> values = new ArrayList<ProcessBillingValuesViewBean>();
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/01/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/05/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/02/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/03/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/07/2011"));
		
		OrderService requestService = new OrderService();
		
		try {
			requestService.processBilling(retOrder,OrderService.PAYMENT_TYPE_INSTALLMENTS,values);
			Assert.fail("O faturamento tem que falhar se os meses passados nao estiverem em sequencia");
		} catch (POSystemException exc) {
			Assert.assertEquals("Por favor, informe das parcelas em sequ&ecirc;ncia.", exc.getSystemMessages());
		}
	}
	@Test
	public void processBilling_PaymentTypeNoInstallments_MoreThanOneInstallment() {
//		//Salvar cliente
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc(String.valueOf(System.currentTimeMillis()));
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setName("MBB");
		customer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		executor.executeCommand(new SaveCustomerCommand(customer));
		
		//Adicionar request
		HibernateExecutor<Order> reqExecutor = new HibernateExecutor<Order>();
		
		//Montar request false
		Order order = new Order();
		order.setCustomer(customer);
		order.setDescription("XXXXX");
		order.setOpenDate(new Date());
		order.setPrice(100.99);
		order.setQuantity(102);
		order.setStatus(OrderStatus.CLOSED);
		order.setCloseDate(new Date());
		order.setOrderCode("ORDER_CODE" + System.currentTimeMillis());
		order.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		Order retRequest = reqExecutor.executeCommand(new SaveOrderCommand(order));
		
		List<ProcessBillingValuesViewBean> values = new ArrayList<ProcessBillingValuesViewBean>();
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/01/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/05/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/02/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/03/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/07/2011"));
		
		OrderService requestService = new OrderService();
		
		try {
			requestService.processBilling(retRequest,OrderService.PAYMENT_TYPE_NO_INSTALLMENTS,values);
			Assert.fail("O faturamento tem que falhar se for passado mais de uma parcela para pagamento a vista.");
		} catch (POSystemException exc) {
			Assert.assertEquals("Pagamento a vista deve ter somente uma parcela.", exc.getSystemMessages());
		}
	}
	@Test
	public void processBillingTest_RestService_FiveInstallments() {
//		//Salvar cliente
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc(String.valueOf(System.currentTimeMillis()));
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setName("MBB");
		customer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		executor.executeCommand(new SaveCustomerCommand(customer));
		
		//Adicionar request
		HibernateExecutor<Order> reqExecutor = new HibernateExecutor<Order>();
		
		//Montar request false
		Order order = new Order();
		order.setCustomer(customer);
		order.setDescription("XXXXX");
		order.setOpenDate(new Date());
		order.setPrice(100.99);
		order.setQuantity(102);
		order.setStatus(OrderStatus.CLOSED);
		order.setCloseDate(new Date());
		order.setOrderCode("ORDER_CODE" + System.currentTimeMillis());
		order.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		Order retOrder = reqExecutor.executeCommand(new SaveOrderCommand(order));
		
		List<ProcessBillingValuesViewBean> values = new ArrayList<ProcessBillingValuesViewBean>();
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/01/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/02/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/03/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/04/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/05/2011"));
		
		
		OrderService orderService = new OrderService();
		Order updatedOrder = orderService.processBilling(retOrder, OrderService.PAYMENT_TYPE_INSTALLMENTS, values);
		Assert.assertNotNull(updatedOrder);
	}
	@Test
	public void processBillingTest_InstallmentsStartingOnDec() {
//		//Salvar cliente
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc(String.valueOf(System.currentTimeMillis()));
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setName("MBB");
		customer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		executor.executeCommand(new SaveCustomerCommand(customer));
		
		//Adicionar request
		HibernateExecutor<Order> reqExecutor = new HibernateExecutor<Order>();
		
		//Montar request false
		Order order = new Order();
		order.setCustomer(customer);
		order.setDescription("XXXXX");
		order.setOpenDate(new Date());
		order.setPrice(100.99);
		order.setQuantity(102);
		order.setStatus(OrderStatus.CLOSED);
		order.setCloseDate(new Date());
		order.setOrderCode("ORDER_CODE" + System.currentTimeMillis());
		order.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		Order retOrder = reqExecutor.executeCommand(new SaveOrderCommand(order));
		
		List<ProcessBillingValuesViewBean> values = new ArrayList<ProcessBillingValuesViewBean>();
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/11/2010"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/12/2010"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/01/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/02/2011"));
		values.add(new ProcessBillingValuesViewBean("R$ 150,30", "01/03/2011"));
		
		
		OrderService orderService = new OrderService();
		Order updatedOrder = orderService.processBilling(retOrder, OrderService.PAYMENT_TYPE_INSTALLMENTS, values);
		Assert.assertNotNull(updatedOrder);
	}
}

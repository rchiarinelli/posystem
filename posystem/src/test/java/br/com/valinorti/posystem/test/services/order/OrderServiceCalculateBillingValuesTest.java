/**
 * 
 */
package br.com.valinorti.posystem.test.services.order;

import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SaveCustomerCommand;
import br.valinorti.posystem.command.SaveOrderCommand;
import br.valinorti.posystem.command.SavePersistentEntityCommand;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.service.OrderService;
import br.valinorti.posystem.service.order.view.ProcessBillingValuesViewBean;
import br.valinorti.util.POSystemUtils;

/**
 * @author rchiari
 *
 */
public class OrderServiceCalculateBillingValuesTest extends BaseJUnitTest {

	private Subscriber subscriber;
	
	/* (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	public void prepare() throws Exception {
		super.prepare();
		if (this.subscriber==null) {
			this.subscriber = new Subscriber();
			this.subscriber.setCity("FAke");
			this.subscriber.setComplement("Fake");
			this.subscriber.setDocument("Fake");
			this.subscriber.setEmail("N<AANMAA");
			this.subscriber.setName("IUOIUOIASUD");
			this.subscriber.setNumber("IASUDOIASUDOASD");
			this.subscriber.setStreet("khsakdhsda");
			
			this.subscriber.setZipCode("IASYDIUASYDAIUSDy");
			
			HibernateExecutor<Subscriber> subscriberExec = new HibernateExecutor<Subscriber>();
			subscriberExec.executeCommand(new SavePersistentEntityCommand<Subscriber>(this.subscriber));
		}
	}
	
	@Test
	public void testCalculateBillingValues_Installments() {
		
//		//Salvar Customer
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc(String.valueOf(System.currentTimeMillis()));
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setName("MBB");
		customer.setSubscriber(subscriber);
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
		order.setSubscriber(this.subscriber);
		
		Order retRequest = reqExecutor.executeCommand(new SaveOrderCommand(order));
		
		//33,663
		OrderService requestService = new OrderService();
		List<ProcessBillingValuesViewBean> results = requestService.calculateBillingValues(retRequest, OrderService.PAYMENT_TYPE_INSTALLMENTS, 3);
		
		Assert.assertNotNull(results);
		Assert.assertFalse(results.isEmpty());
		
		for (ProcessBillingValuesViewBean bean : results) {
			Assert.assertEquals("R$ 33,66", bean.getValue());
		}
		
	}
	
	@Test
	public void testCalculateBillingValues_NoInstallments() {
		
//		//Salvar Customer
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer customer = new PJCustomer();
		customer.setCnpjCgc(String.valueOf(System.currentTimeMillis()));
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setName("MBB");
		customer.setSubscriber(subscriber);
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
		order.setSubscriber(this.subscriber);
		
		Order retRequest = reqExecutor.executeCommand(new SaveOrderCommand(order));
		
		//100,99
		OrderService requestService = new OrderService();
		List<ProcessBillingValuesViewBean> results = requestService.calculateBillingValues(retRequest, OrderService.PAYMENT_TYPE_NO_INSTALLMENTS, 1);
		
		Assert.assertNotNull(results);
		Assert.assertFalse(results.isEmpty());
		
		
		String expectedDate = POSystemUtils.formatDate(new Date());
		
		for (ProcessBillingValuesViewBean bean : results) {
			Assert.assertEquals("R$ 100,99", bean.getValue());
			Assert.assertEquals(expectedDate, bean.getDate());
		}
	}
	
}

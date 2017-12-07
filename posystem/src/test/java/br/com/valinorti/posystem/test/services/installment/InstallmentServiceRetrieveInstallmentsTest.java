/**
 * 
 */
package br.com.valinorti.posystem.test.services.installment;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SaveBillingCommand;
import br.valinorti.posystem.command.SaveCustomerCommand;
import br.valinorti.posystem.command.SaveOrderCommand;
import br.valinorti.posystem.command.SavePersistentEntityCommand;
import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.entity.BillingStatus;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.entity.PaymentType;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.service.InstallmentService;

/**
 * @author leafar
 *
 */
public class InstallmentServiceRetrieveInstallmentsTest extends BaseJUnitTest {

	private Order order;
	
	
	/* (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	@Before
	public void prepare() throws Exception {
		super.prepare();
		if (this.order==null) {
			Subscriber subscriber = new Subscriber();
			subscriber.setCity("FAke");
			subscriber.setComplement("Fake");
			subscriber.setDocument("Fake");
			subscriber.setEmail("N<AANMAA");
			subscriber.setName("IUOIUOIASUD");
			subscriber.setNumber("IASUDOIASUDOASD");
			subscriber.setStreet("khsakdhsda");
			
			subscriber.setZipCode("IASYDIUASYDAIUSDy");
			
			HibernateExecutor<Subscriber> subscriberExec = new HibernateExecutor<Subscriber>();
			subscriberExec.executeCommand(new SavePersistentEntityCommand<Subscriber>(subscriber));			
			
			//Salvar cliente
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
			order.setSubscriber(subscriber);
			
			Order retRequest = reqExecutor.executeCommand(new SaveOrderCommand(order));

			Billing billing = new Billing();
			billing.setDate(new Date());
			billing.setFinalValue(500.30D);
			billing.setOrder(retRequest);
			billing.setPaymentType(PaymentType.INSTALLMENTS);
			billing.setSubscriber(subscriber);
			
			Calendar calendar = Calendar.getInstance();
			calendar.setLenient(false);
			calendar.setTime(new Date());
			
			Set<Installment> installments = new HashSet<Installment>();
			installments.add(new Installment(5.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			
			installments.add(new Installment(10.5D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(10.5D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(10.5D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(10.5D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(10.5D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(10.5D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));

			calendar.add(Calendar.DAY_OF_YEAR, 5);
			installments.add(new Installment(50.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(50.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(50.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(50.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(50.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(50.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));

			
			calendar.add(Calendar.DAY_OF_YEAR, 10);
			installments.add(new Installment(100.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(100.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(100.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(100.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(100.2D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			
			calendar.add(Calendar.DAY_OF_YEAR, 20);
			installments.add(new Installment(230.1D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(230.1D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(230.1D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));

			
			calendar.add(Calendar.DAY_OF_YEAR, 30);
			installments.add(new Installment(150.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(150.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(150.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			
			calendar.add(Calendar.DAY_OF_YEAR, 40);
			installments.add(new Installment(360.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(360.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			installments.add(new Installment(360.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber));
			
			billing.setInstallments(installments);
			billing.setStatus(BillingStatus.OPEN);
			
			HibernateExecutor<Billing> billingExecutor = new HibernateExecutor<Billing>();
			billingExecutor.executeCommand(new SaveBillingCommand(billing));
		}
	}

	@Test
	public void retrieveInstallment_Next10Days() {
		InstallmentService installmentService = new InstallmentService();
		int days = 10;
		List<Installment> installments = installmentService.retrievePendingInstallments(days);
		Assert.assertNotNull(installments);
		Assert.assertFalse(installments.isEmpty());
	}
}

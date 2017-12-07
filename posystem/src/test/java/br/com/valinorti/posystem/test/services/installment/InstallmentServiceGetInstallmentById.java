/**
 * 
 */
package br.com.valinorti.posystem.test.services.installment;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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
 * @author rchiari
 *
 */
public class InstallmentServiceGetInstallmentById extends BaseJUnitTest {

	private Installment expectedInstallment;
	
	
	/* (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	@Before
	public void prepare() throws Exception {
		super.prepare();
		if (this.expectedInstallment==null) {
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
			Order request = new Order();
			request.setCustomer(customer);
			request.setDescription("XXXXX");
			request.setOpenDate(new Date());
			request.setPrice(100.99);
			request.setQuantity(102);
			request.setStatus(OrderStatus.CLOSED);
			request.setCloseDate(new Date());
			request.setOrderCode("ORDER_CODE" + System.currentTimeMillis());
			request.setSubscriber(subscriber);
			
			
			Order retRequest = reqExecutor.executeCommand(new SaveOrderCommand(request));

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
			installments.add(new Installment(5.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber ));
			
			calendar.add(Calendar.DAY_OF_YEAR, 1);
			
			Installment installment = new Installment(10.5D, calendar.getTime(), InstallmentStatus.PENDING,billing,subscriber);
			
			installments.add(installment);
			
			billing.setInstallments(installments);
			billing.setStatus(BillingStatus.OPEN);
			
			HibernateExecutor<Billing> billingExecutor = new HibernateExecutor<Billing>();
			billingExecutor.executeCommand(new SaveBillingCommand(billing));
			this.expectedInstallment = installment;
		}
	}
	@Test
	public void testGetInstallmentById() {
		InstallmentService installmentService = new InstallmentService();
		Installment installment = installmentService.getById(this.expectedInstallment.getId());
		Assert.assertNotNull(installment);
	}
}

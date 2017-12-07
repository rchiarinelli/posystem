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
import br.valinorti.util.HibernateUtils;

/**
 * @author leafar
 *
 */
public class InstallmentServicePayInstallmentTest extends BaseJUnitTest {

	private Installment installment;
	
	
	/* (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	@Before
	public void prepare() throws Exception {
		super.prepare();
		if (this.installment==null) {
			//Salvar cliente
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

			Billing billing = new Billing();
			billing.setDate(new Date());
			billing.setFinalValue(500.30D);
			billing.setOrder(retRequest);
			billing.setPaymentType(PaymentType.INSTALLMENTS);
			billing.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
			
			
			Calendar calendar = Calendar.getInstance();
			calendar.setLenient(false);
			calendar.setTime(new Date());
			
			this.installment =  new Installment(5.3D, calendar.getTime(), InstallmentStatus.PENDING,billing,(Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
			
			Set<Installment> installments = new HashSet<Installment>();
			installments.add(this.installment);
			
			
			billing.setInstallments(installments);
			billing.setStatus(BillingStatus.OPEN);
			
			
			HibernateExecutor<Billing> billingExecutor = new HibernateExecutor<Billing>();
			billingExecutor.executeCommand(new SaveBillingCommand(billing));
		}
	}
	
	@Test
	public void testPayInstallment_OnDate() {
		this.installment.setFinalValue(this.installment.getValue());
		this.installment.setStatus(InstallmentStatus.PAID);
		Date today = new Date();
		
		this.installment.setPaymentDate(today);
		InstallmentService service = new InstallmentService();
		Installment updatedObj = service.payInstallment(this.installment);
		Assert.assertNotNull(updatedObj);
		
		Assert.assertEquals(this.installment.getDate(), updatedObj.getDate());
		Assert.assertEquals(this.installment.getFinalValue(),updatedObj.getFinalValue());
		Assert.assertEquals(this.installment.getId(),updatedObj.getId());
		Assert.assertEquals(this.installment.getPaymentDate(),updatedObj.getPaymentDate());
		Assert.assertEquals(this.installment.getStatus(), updatedObj.getStatus());
		Assert.assertEquals(this.installment.getValue(), updatedObj.getValue());
	}
	
//	@Test
//	public void testPayInstallment_AfterDateSameValue() {
//		try {
//			this.installment.setStatus(InstallmentStatus.PAID);
//			this.installment.setFinalValue(this.installment.getValue());
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(this.installment.getDate());
//			calendar.add(Calendar.DAY_OF_YEAR, 3);
//			
//			Date paymentDate = calendar.getTime();
//			this.installment.setPaymentDate(paymentDate);
//			
//			InstallmentService service = new InstallmentService();
//			service.payInstallment(this.installment);
//			Assert.fail("O servico deveria lancar uma excecao por conta do pagamento sem juros após a data de vencimento");
//		} catch (POSystemException exc) {
//			Assert.assertNotNull(exc);
//		}
//	}
}

/**
 * 
 */
package br.com.valinorti.posystem.test.facade;

import static org.junit.Assert.*;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.GetBillingByIdCommand;
import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.entity.BillingStatus;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;
import br.valinorti.posystem.service.BillingFacade;

/**
 * @author leafar
 *
 */
public class BillingFacadeUpdateBillingTest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Test
	public void testUpdateBilling_OpenBillingAllInstallmentsPaid() {
		Billing billing = new Billing();
		billing.setStatus(BillingStatus.OPEN);
		Set<Installment> installments = new HashSet<Installment>();
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		billing.setInstallments(installments);
		
		
		HibernateExecutor mockedExecutor = mock(HibernateExecutor.class);
		when(mockedExecutor.executeCommand(any(GetBillingByIdCommand.class))).thenReturn(billing);
		BillingFacade billingFacade = new BillingFacade(mockedExecutor);
		Billing updatedBilling = billingFacade.updateBillingStatus(billing);
		assertNotNull("Billing nao deve ser nulo",updatedBilling);
		assertEquals("O status deve ser CLOSED",BillingStatus.CLOSED,updatedBilling.getStatus());
	}
	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testUpdateBilling_OpenBillingOneInstallmentsIsPending() {
		Billing billing = new Billing();
		billing.setStatus(BillingStatus.OPEN);
		Set<Installment> installments = new HashSet<Installment>();
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PENDING));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		installments.add(new Installment(Double.valueOf(200.00), new Date(),InstallmentStatus.PAID));
		billing.setInstallments(installments);
		
		
		HibernateExecutor mockedExecutor = mock(HibernateExecutor.class);
		when(mockedExecutor.executeCommand(any(GetBillingByIdCommand.class))).thenReturn(billing);
		BillingFacade billingFacade = new BillingFacade(mockedExecutor);
		Billing updatedBilling = billingFacade.updateBillingStatus(billing);
		assertNotNull("Billing nao deve ser nulo",updatedBilling);
		assertEquals("O status deve ser OPEN",BillingStatus.OPEN,updatedBilling.getStatus());
	} 
	
	@Test
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void testUpdateBilling_ClosedBilling() {
		Billing billing = new Billing();
		billing.setStatus(BillingStatus.CLOSED);
		
		HibernateExecutor mockedExecutor = mock(HibernateExecutor.class);
		when(mockedExecutor.executeCommand(any(GetBillingByIdCommand.class))).thenReturn(billing);
		BillingFacade billingFacade = new BillingFacade(mockedExecutor);
		Billing updatedBilling = billingFacade.updateBillingStatus(billing);
		assertNotNull("Billing nao deve ser nulo",updatedBilling);
		assertEquals("O status deve ser CLOSED",BillingStatus.CLOSED,updatedBilling.getStatus());
	} 
	
}

/**
 * 
 */
package br.com.valinorti.posystem.test.command.customer;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SaveCustomerCommand;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.util.HibernateUtils;

/**
 * @author Rafael Chiarinelli
 *
 */
public class SavePFCustomerCommandTest extends BaseJUnitTest {
	
	@Test
	public void testSavePFCustomer(){
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PFCustomer customer = new PFCustomer();
		//Honda
		customer.setName("Honda");
		customer.setCpf("11111111L");
		customer.setStatus(CustomerStatus.ACTIVE);
		customer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		
		Customer retCustomer = executor.executeCommand(new SaveCustomerCommand(customer));
		Assert.assertNotNull(retCustomer);
		Assert.assertEquals(customer.getName(), retCustomer.getName());
	}
	
	@Test
	public void testSavePFCustomerWithAddress(){
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PFCustomer customer = new PFCustomer();
		//Honda
		customer.setName("Honda");
		customer.setCpf("11111111L");
		customer.setStatus(CustomerStatus.ACTIVE);
		//Address
		CustomerAddress address = new CustomerAddress();
		address.setStreet("Rua Catarina Teixeira de Camargo");
		address.setCustomer(customer);
		
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(address);
		
		customer.setAddresses(addresses);
		
		customer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().openSession().get(Subscriber.class, new Integer(1)));
		
		Customer retCustomer = executor.executeCommand(new SaveCustomerCommand(customer));
		Assert.assertNotNull(retCustomer);
		Assert.assertEquals(customer.getName(), retCustomer.getName());
	}	
}

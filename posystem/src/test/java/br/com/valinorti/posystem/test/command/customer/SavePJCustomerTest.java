/**
 * 
 */
package br.com.valinorti.posystem.test.command.customer;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;
import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.SaveCustomerCommand;
import br.valinorti.posystem.command.SavePersistentEntityCommand;
import br.valinorti.posystem.entity.ContactStatus;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.entity.Subscriber;

/**
 * @author leafar
 *
 */
public class SavePJCustomerTest extends BaseJUnitTest {

	
	private Subscriber subscriber;
	
	
	
	
	/* (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	@Before
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
	public void testSavePJCustomer_SimpleField() {
		PJCustomer pjCustomer = new PJCustomer();
		pjCustomer.setDetails("PF Detail field.");
		pjCustomer.setName("Fake name");
		pjCustomer.setStatus(CustomerStatus.ACTIVE);
		pjCustomer.setCnpjCgc("1000000000");
		pjCustomer.setInscrEstadual(2000L);
		pjCustomer.setSubscriber(subscriber);
		
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer result = (PJCustomer) executor.executeCommand(new SaveCustomerCommand(pjCustomer));
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertTrue(result.getId() > 0);
		Assert.assertEquals(pjCustomer.getDetails(), result.getDetails());
		Assert.assertEquals(pjCustomer.getName(), result.getName());
		Assert.assertEquals(pjCustomer.getCnpjCgc(),result.getCnpjCgc());
		Assert.assertEquals(pjCustomer.getInscrEstadual(),result.getInscrEstadual());
		Assert.assertEquals(pjCustomer.getInscrMunicipal(),result.getInscrMunicipal());
		Assert.assertEquals(pjCustomer.getStatus(),result.getStatus());
	}
	
	@Test
	public void testSavePJCustomer_Addresses() {
		PJCustomer pjCustomer = new PJCustomer();
		pjCustomer.setDetails("PF Detail field.");
		pjCustomer.setName("Fake name");
		pjCustomer.setStatus(CustomerStatus.ACTIVE);
		pjCustomer.setCnpjCgc("1000000000");
		pjCustomer.setInscrEstadual(2000L);
		pjCustomer.setSubscriber(subscriber);
		//Addresses
		CustomerAddress address = new CustomerAddress();
		address.setStreet("1st Street");
		address.setCustomer(pjCustomer);
		
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(address);
		
		pjCustomer.setAddresses(addresses);
		
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer result = (PJCustomer) executor.executeCommand(new SaveCustomerCommand(pjCustomer));
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertTrue(result.getId() > 0);
		Assert.assertEquals(pjCustomer.getDetails(), result.getDetails());
		Assert.assertEquals(pjCustomer.getName(), result.getName());
		Assert.assertEquals(pjCustomer.getCnpjCgc(),result.getCnpjCgc());
		Assert.assertEquals(pjCustomer.getInscrEstadual(),result.getInscrEstadual());
		Assert.assertEquals(pjCustomer.getInscrMunicipal(),result.getInscrMunicipal());
		Assert.assertEquals(pjCustomer.getStatus(),result.getStatus());
		
		Assert.assertNotNull(result.getAddresses());
		Assert.assertFalse(result.getAddresses().isEmpty());
		
		Set<CustomerAddress> expectedAddresses = pjCustomer.getAddresses();
		Set<CustomerAddress> currentAddresses = result.getAddresses();
		
		boolean found = false;
		
		for (CustomerAddress expectedAddr : expectedAddresses) {
			for (CustomerAddress currAddr : currentAddresses) {
				found = ((StringUtils.equals(expectedAddr.getCity(), currAddr.getCity())) 
							&& (StringUtils.equals(expectedAddr.getComplement(), currAddr.getCity()))
							&& (StringUtils.equals(expectedAddr.getCountry(), currAddr.getCountry()))
							&& (StringUtils.equals(expectedAddr.getState(), currAddr.getState()))
							&& (StringUtils.equals(expectedAddr.getStreet(), currAddr.getStreet()))
							&& (StringUtils.equals(expectedAddr.getZipCode(), currAddr.getZipCode()))
							&& (expectedAddr.getNumber() ==  currAddr.getNumber()));
			}
		}
		
		Assert.assertTrue(found);
		
	}
	
	@Test
	public void testSavePJCustomer_Contacts() {
		PJCustomer pjCustomer = new PJCustomer();
		pjCustomer.setDetails("PF Detail field.");
		pjCustomer.setName("Fake name");
		pjCustomer.setStatus(CustomerStatus.ACTIVE);
		pjCustomer.setCnpjCgc("30000L");
		pjCustomer.setInscrEstadual(4000L);
		pjCustomer.setSubscriber(subscriber);
		//Contacts
		
		CustomerContact contact = new CustomerContact();
		contact.setCustomer(pjCustomer);
		contact.setEmail("fake@email.com");
		contact.setMobilePhoneNumber("(99)9999-9999");
		contact.setName("FAKE NAME");
		contact.setOfficePhoneNumber("(99)4444-4444");
		contact.setStatus(ContactStatus.ACTIVE);
		
		Set<CustomerContact> contactsSet = new HashSet<CustomerContact>();
		contactsSet.add(contact);
		
		pjCustomer.setContacts(contactsSet);
		
		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
		PJCustomer result = (PJCustomer) executor.executeCommand(new SaveCustomerCommand(pjCustomer));
		
		Assert.assertNotNull(result);
		Assert.assertNotNull(result.getId());
		Assert.assertTrue(result.getId() > 0);
		Assert.assertEquals(pjCustomer.getDetails(), result.getDetails());
		Assert.assertEquals(pjCustomer.getName(), result.getName());
		Assert.assertEquals(pjCustomer.getCnpjCgc(),result.getCnpjCgc());
		Assert.assertEquals(pjCustomer.getInscrEstadual(),result.getInscrEstadual());
		Assert.assertEquals(pjCustomer.getInscrMunicipal(),result.getInscrMunicipal());
		Assert.assertEquals(pjCustomer.getStatus(),result.getStatus());
		
		Assert.assertNotNull(result.getContacts());
		Assert.assertFalse(result.getContacts().isEmpty());
		
		
		boolean found = false;
		
		Set<CustomerContact> expectedContacts = pjCustomer.getContacts();
		Set<CustomerContact> currentContacts = result.getContacts();
		
		for (CustomerContact expectedCont : expectedContacts) {
			for (CustomerContact currCont : currentContacts) {
				found = ((StringUtils.equals(expectedCont.getEmail(), currCont.getEmail()))
						&& (StringUtils.equals(expectedCont.getMobilePhoneNumber(), currCont.getMobilePhoneNumber()))
						&& (StringUtils.equals(expectedCont.getName(), currCont.getName()))
						&& (StringUtils.equals(expectedCont.getOfficePhoneNumber(), currCont.getOfficePhoneNumber()))
						&& (expectedCont.getStatus()==currCont.getStatus()));
			}
		}
		
		
		Assert.assertTrue(found);		
	}
}

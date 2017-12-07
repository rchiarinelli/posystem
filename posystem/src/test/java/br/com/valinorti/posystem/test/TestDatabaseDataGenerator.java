/**
 * 
 */
package br.com.valinorti.posystem.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Ignore;

import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.entity.BillingStatus;
import br.valinorti.posystem.entity.ContactStatus;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerAddress;
import br.valinorti.posystem.entity.CustomerContact;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;
import br.valinorti.posystem.entity.Order;
import br.valinorti.posystem.entity.OrderStatus;
import br.valinorti.posystem.entity.PFCustomer;
import br.valinorti.posystem.entity.PJCustomer;
import br.valinorti.posystem.entity.POStatus;
import br.valinorti.posystem.entity.PaymentType;
import br.valinorti.posystem.entity.Permission;
import br.valinorti.posystem.entity.PermissionKey;
import br.valinorti.posystem.entity.PermissionKey.Operation;
import br.valinorti.posystem.entity.ProductionOrder;
import br.valinorti.posystem.entity.Resource;
import br.valinorti.posystem.entity.Subscriber;
import br.valinorti.posystem.entity.User;
import br.valinorti.util.HibernateUtils;

/**
 * @author leafar
 *
 */
@Ignore
public class TestDatabaseDataGenerator {

	private TestDatabaseDataGenerator() {
	}
	
	private static boolean isDataGenerated;
	/**
	 * Gera uma massa de dados a ser usada nos testes.
	 * 
	 */
	public static void generateDBData() {
		if (!isDataGenerated) {
			//Gerar Resource
			generateResourceDBData();
			//Gerar subscriber
			generateSubscriberDBData();
			//Gerar usuario
			generateUserDBData();
			//Gerar cliente pf
			generatePFCustomerDBData();
			//Gerar cliente pj
			generatePJCustomerDBData();
			//Gerar Pedido
			generateOrderDBData();
			//Gerar Ordem de servico
			generateProductionOrderDBData();
			//Gerar faturamento
			generateBillingDBData();
		
			isDataGenerated = true;
		}
	}
	
	private static void generateSubscriberDBData() {
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		//Gerar os subscribers
		Subscriber subscriber = new Subscriber();
		subscriber.setCity("Subscriber_1");
		subscriber.setComplement("Subscriber_1");
		subscriber.setDocument("Subscriber_1");
		subscriber.setEmail("Subscriber_1");
		subscriber.setName("Subscriber_1");
		subscriber.setNumber("Subscriber_1");
		subscriber.setStreet("Subscriber_1");
		subscriber.setZipCode("Subscriber_1");
		HibernateUtils.getSessionFactory().getCurrentSession().save(subscriber);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		subscriber = new Subscriber();
		subscriber.setCity("Subscriber_2");
		subscriber.setComplement("Subscriber_2");
		subscriber.setDocument("Subscriber_2");
		subscriber.setEmail("Subscriber_2");
		subscriber.setName("Subscriber_2");
		subscriber.setNumber("Subscriber_2");
		subscriber.setStreet("Subscriber_2");
		subscriber.setZipCode("Subscriber_2");
		HibernateUtils.getSessionFactory().getCurrentSession().save(subscriber);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		subscriber = new Subscriber();
		subscriber.setCity("Subscriber_3");
		subscriber.setComplement("Subscriber_3");
		subscriber.setDocument("Subscriber_3");
		subscriber.setEmail("Subscriber_3");
		subscriber.setName("Subscriber_3");
		subscriber.setNumber("Subscriber_3");
		subscriber.setStreet("Subscriber_3");
		subscriber.setZipCode("Subscriber_3");
		HibernateUtils.getSessionFactory().getCurrentSession().save(subscriber);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
	}
	/**
	 * 
	 * @param session
	 */
	private static void generateUserDBData() {
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		//Gerar os usuarios
		User user = new User();
		user.setEmail("user_email_1");
		user.setLogin("user_loginl_1");
		user.setPassword("user_password_1");
		user.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().getCurrentSession().get(Subscriber.class, new Integer(1)));
		generateUserPermissionsDBData(user);
		HibernateUtils.getSessionFactory().getCurrentSession().save(user);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();		
		user = new User();
		user.setEmail("user_email_2");
		user.setLogin("user_loginl_2");
		user.setPassword("user_password_2");
		user.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().getCurrentSession().get(Subscriber.class, new Integer(2)));
		generateUserPermissionsDBData(user);
		HibernateUtils.getSessionFactory().getCurrentSession().save(user);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
	}
	/**
	 * 
	 * @param session
	 * @param user
	 */
	private static void generateUserPermissionsDBData(User user) {
		Set<Permission> permissions = new HashSet<Permission>();

		//Permissao 1
		Resource res = (Resource) HibernateUtils.getSessionFactory().getCurrentSession().get(Resource.class, new Integer(1));
		Permission permission = new Permission();
		permission.setComponsiteId(new PermissionKey(user,res,Operation.ADD));
		permissions.add(permission);
		
		//Permissao 2
		res = (Resource) HibernateUtils.getSessionFactory().getCurrentSession().get(Resource.class, new Integer(1));
		permission = new Permission();
		permission.setComponsiteId(new PermissionKey(user,res,Operation.READ));
		permissions.add(permission);
		
		
		//Permissao 3
		res = (Resource) HibernateUtils.getSessionFactory().getCurrentSession().get(Resource.class, new Integer(2));
		permission = new Permission();
		permission.setComponsiteId(new PermissionKey(user,res,Operation.READ));
		permissions.add(permission);
		
		//Permissao 4
		res = (Resource) HibernateUtils.getSessionFactory().getCurrentSession().get(Resource.class, new Integer(2));
		permission = new Permission();
		permission.setComponsiteId(new PermissionKey(user,res,Operation.ADD));
		permissions.add(permission);
		
		user.setPermissions(permissions);
	}
	
	/**
	 * 
	 * @param session
	 */
	private static void generateResourceDBData() {
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		Resource resource = new Resource();
		resource.setName("Resource_1");
		HibernateUtils.getSessionFactory().getCurrentSession().save(resource);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		resource = new Resource();
		resource.setName("Resource_2");
		HibernateUtils.getSessionFactory().getCurrentSession().save(resource);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		resource = new Resource();
		resource.setName("Resource_3");
		HibernateUtils.getSessionFactory().getCurrentSession().save(resource);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		resource = new Resource();
		resource.setName("Resource_4");
		HibernateUtils.getSessionFactory().getCurrentSession().save(resource);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();

	}
	
	/**
	 * 
	 */
	private static void generatePFCustomerDBData() {
		//PFCustomer1
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		PFCustomer pfCustomer = new PFCustomer();
		pfCustomer.setCpf("324324");
		pfCustomer.setDetails("FAKE_PF_CUSTOMER_1");
		pfCustomer.setName("FAKE_PF_CUSTOMER_1");
		pfCustomer.setRg(Long.parseLong("234234324"));
		pfCustomer.setStatus(CustomerStatus.ACTIVE);
		pfCustomer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().getCurrentSession().get(Subscriber.class, new Integer(1)));
		//Address
		CustomerAddress address = new CustomerAddress();
		address.setStreet("Rua XYZ");
		address.setCustomer(pfCustomer);
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(address);
		pfCustomer.setAddresses(addresses);
		//Contacts
		Set<CustomerContact> contacts = new HashSet<CustomerContact>();
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_1", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_2", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_3", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_4", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		HibernateUtils.getSessionFactory().getCurrentSession().save(pfCustomer);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		//PFCustomer2
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		pfCustomer = new PFCustomer();
		pfCustomer.setCpf("324324");
		pfCustomer.setDetails("FAKE_PF_CUSTOMER_2");
		pfCustomer.setName("FAKE_PF_CUSTOMER_2");
		pfCustomer.setRg(Long.parseLong("234234324"));
		pfCustomer.setStatus(CustomerStatus.ACTIVE);
		pfCustomer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().getCurrentSession().get(Subscriber.class, new Integer(1)));
		//Address
		address = new CustomerAddress();
		address.setStreet("Rua XYZ");
		address.setCustomer(pfCustomer);
		addresses = new HashSet<CustomerAddress>();
		addresses.add(address);
		pfCustomer.setAddresses(addresses);
		//Contacts
		contacts = new HashSet<CustomerContact>();
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_1", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_2", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_3", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_4", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		HibernateUtils.getSessionFactory().getCurrentSession().save(pfCustomer);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		//PFCustomer3		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		pfCustomer = new PFCustomer();
		pfCustomer.setCpf("324324");
		pfCustomer.setDetails("FAKE_PF_CUSTOMER_3");
		pfCustomer.setName("FAKE_PF_CUSTOMER_3");
		pfCustomer.setRg(Long.parseLong("234234324"));
		pfCustomer.setStatus(CustomerStatus.ACTIVE);
		pfCustomer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().getCurrentSession().get(Subscriber.class, new Integer(2)));
		//Address
		address = new CustomerAddress();
		address.setStreet("Rua XYZ");
		address.setCustomer(pfCustomer);
		addresses = new HashSet<CustomerAddress>();
		addresses.add(address);
		pfCustomer.setAddresses(addresses);
		//Contacts
		contacts = new HashSet<CustomerContact>();
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_1", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_2", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_3", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_4", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pfCustomer));
		HibernateUtils.getSessionFactory().getCurrentSession().save(pfCustomer);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();		
	}
	
	private static void generatePJCustomerDBData() {
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		PJCustomer pjCustomer = new PJCustomer();
		pjCustomer.setCnpjCgc("99999999");
		pjCustomer.setDetails("FAKE_PF_CUSTOMER_1");
		pjCustomer.setName("FAKE_PF_CUSTOMER_1");
		pjCustomer.setStatus(CustomerStatus.ACTIVE);
		pjCustomer.setSubscriber((Subscriber)HibernateUtils.getSessionFactory().getCurrentSession().get(Subscriber.class, new Integer(1)));
		//Address
		CustomerAddress address = new CustomerAddress();
		address.setStreet("Rua XYZ");
		address.setCustomer(pjCustomer);
		Set<CustomerAddress> addresses = new HashSet<CustomerAddress>();
		addresses.add(address);
		pjCustomer.setAddresses(addresses);
		//Contacts
		Set<CustomerContact> contacts = new HashSet<CustomerContact>();
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_1", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pjCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_2", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pjCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_3", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pjCustomer));
		contacts.add(new CustomerContact("FAKE_PF_CUST_CONTACT_4", "XXX", "YYY", "ZZZ", ContactStatus.ACTIVE, pjCustomer));
		HibernateUtils.getSessionFactory().getCurrentSession().save(pjCustomer);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
	}
	
	
	
	private static void generateOrderDBData() {
		//Pedido1
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		Order order = new Order();
		Customer customer = (Customer)HibernateUtils.getSessionFactory().getCurrentSession().get(Customer.class, new Integer(1)); 
		order.setCustomer(customer);
		order.setDescription("FAKE_ORDER_1");
		order.setDeliverDate(new Date());
		order.setOpenDate(new Date());
		order.setOrderCode("FAKE_CODE_1");
		order.setPrice(300.5D);
		order.setQuantity(10);
		order.setStatus(OrderStatus.OPEN);
		order.setSubscriber(customer.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(order);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		//Pedido2
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		order = new Order();
		customer = (Customer)HibernateUtils.getSessionFactory().getCurrentSession().get(Customer.class, new Integer(1)); 
		order.setCustomer(customer);
		order.setDescription("FAKE_ORDER_2");
		order.setDeliverDate(new Date());
		order.setOpenDate(new Date());
		order.setOrderCode("FAKE_CODE_2");
		order.setPrice(300.5D);
		order.setQuantity(10);
		order.setStatus(OrderStatus.OPEN);
		order.setSubscriber(customer.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(order);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		//Pedido3
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		order = new Order();
		customer = (Customer)HibernateUtils.getSessionFactory().getCurrentSession().get(Customer.class, new Integer(2)); 
		order.setCustomer(customer);
		order.setDescription("FAKE_ORDER_3");
		order.setDeliverDate(new Date());
		order.setOpenDate(new Date());
		order.setOrderCode("FAKE_CODE_3");
		order.setPrice(300.5D);
		order.setQuantity(10);
		order.setStatus(OrderStatus.OPEN);
		order.setSubscriber(customer.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(order);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		//Pedido4
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		order = new Order();
		customer = (Customer)HibernateUtils.getSessionFactory().getCurrentSession().get(Customer.class, new Integer(2)); 
		order.setCustomer(customer);
		order.setDescription("FAKE_ORDER_4");
		order.setDeliverDate(new Date());
		order.setOpenDate(new Date());
		order.setOrderCode("FAKE_CODE_4");
		order.setPrice(300.5D);
		order.setQuantity(10);
		order.setStatus(OrderStatus.OPEN);
		order.setSubscriber(customer.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(order);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		//Pedido5
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		order = new Order();
		customer = (Customer)HibernateUtils.getSessionFactory().getCurrentSession().get(Customer.class, new Integer(1)); 
		order.setCustomer(customer);
		order.setDescription("FAKE_ORDER_5");
		order.setDeliverDate(new Date());
		order.setOpenDate(new Date());
		order.setOrderCode("FAKE_CODE_5");
		order.setPrice(300.5D);
		order.setQuantity(10);
		order.setStatus(OrderStatus.OPEN);
		order.setSubscriber(customer.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(order);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();		
	}
	
	
	private static void generateProductionOrderDBData() {
		//OS 1
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		ProductionOrder po = new ProductionOrder();
		po.setDeliverDate(new Date());
		po.setDueDate(new Date());
		po.setEstimatedTime(3.5D);
		po.setOpenDate(new Date());
		Order order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(1));
		po.setOrder(order);
		po.setPoNumber(1);
		po.setQtd(10);
		po.setSketchNumber("FAKE_SKETCH");
		po.setStatus(POStatus.PLANNING);
		po.setSubscriber(order.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(po);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		//OS 2
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		po = new ProductionOrder();
		po.setDeliverDate(new Date());
		po.setDueDate(new Date());
		po.setEstimatedTime(3.5D);
		po.setOpenDate(new Date());
		order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(1));
		po.setOrder(order);
		po.setPoNumber(1);
		po.setQtd(10);
		po.setSketchNumber("FAKE_SKETCH");
		po.setStatus(POStatus.PLANNING);
		po.setSubscriber(order.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(po);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		//OS 3
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		po = new ProductionOrder();
		po.setDeliverDate(new Date());
		po.setDueDate(new Date());
		po.setEstimatedTime(3.5D);
		po.setOpenDate(new Date());
		order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(1));
		po.setOrder(order);
		po.setPoNumber(1);
		po.setQtd(10);
		po.setSketchNumber("FAKE_SKETCH");
		po.setStatus(POStatus.PLANNING);
		po.setSubscriber(order.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(po);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();			
		//OS 4
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		po = new ProductionOrder();
		po.setDeliverDate(new Date());
		po.setDueDate(new Date());
		po.setEstimatedTime(3.5D);
		po.setOpenDate(new Date());
		order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(4));
		po.setOrder(order);
		po.setPoNumber(1);
		po.setQtd(10);
		po.setSketchNumber("FAKE_SKETCH");
		po.setStatus(POStatus.PLANNING);
		po.setSubscriber(order.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(po);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		//OS 5
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		po = new ProductionOrder();
		po.setDeliverDate(new Date());
		po.setDueDate(new Date());
		po.setEstimatedTime(3.5D);
		po.setOpenDate(new Date());
		order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(4));
		po.setOrder(order);
		po.setPoNumber(1);
		po.setQtd(10);
		po.setSketchNumber("FAKE_SKETCH");
		po.setStatus(POStatus.PLANNING);
		po.setSubscriber(order.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(po);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		//OS 5
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		po = new ProductionOrder();
		po.setDeliverDate(new Date());
		po.setDueDate(new Date());
		po.setEstimatedTime(3.5D);
		po.setOpenDate(new Date());
		order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(4));
		po.setOrder(order);
		po.setPoNumber(1);
		po.setQtd(10);
		po.setSketchNumber("FAKE_SKETCH");
		po.setStatus(POStatus.PLANNING);
		po.setSubscriber(order.getSubscriber());
		HibernateUtils.getSessionFactory().getCurrentSession().save(po);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
	}
	
	private static void generateBillingDBData() {
		//Billing1
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		Billing billing = new Billing();
		Order order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(1));
		billing.setOrder(order);
		billing.setInstallments(new HashSet<Installment>());
		billing.getInstallments().add(new Installment(20.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(20.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(20.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(20.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(20.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(20.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.setDate(new Date());
		billing.setPaymentType(PaymentType.INSTALLMENTS);
		billing.setStatus(BillingStatus.OPEN);
		billing.setSubscriber(order.getSubscriber());
		billing.setFinalValue(billing.getInstallments().size() * 20.00D);
		HibernateUtils.getSessionFactory().getCurrentSession().save(billing);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
		
		//Billing2		
		HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
		billing = new Billing();
		order = (Order) HibernateUtils.getSessionFactory().getCurrentSession().get(Order.class, new Long(2));
		billing.setOrder(order);
		billing.setInstallments(new HashSet<Installment>());
		billing.getInstallments().add(new Installment(12.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(12.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(12.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(12.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(12.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.getInstallments().add(new Installment(12.00D, new Date(), InstallmentStatus.PENDING, billing, order.getSubscriber()));
		billing.setDate(new Date());
		billing.setPaymentType(PaymentType.INSTALLMENTS);
		billing.setStatus(BillingStatus.OPEN);
		billing.setSubscriber(order.getSubscriber());
		billing.setFinalValue(billing.getInstallments().size() * 12.00D);
		HibernateUtils.getSessionFactory().getCurrentSession().save(billing);
		HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();		
	}
}

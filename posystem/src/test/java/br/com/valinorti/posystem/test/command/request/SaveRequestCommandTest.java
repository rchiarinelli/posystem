/**
 * 
 */
package br.com.valinorti.posystem.test.command.request;

import org.junit.Test;

import br.com.valinorti.posystem.test.BaseJUnitTest;

/**
 * @author Rafael Chiarinelli
 *
 */
public class SaveRequestCommandTest extends BaseJUnitTest {
	/*
	 * (non-Javadoc)
	 * @see br.com.valinorti.posystem.test.BaseJUnitTest#prepare()
	 */
	@Override
	public void prepare() throws Exception {
//		super.prepare();
//		//Salvar Customer
//		HibernateExecutor<Customer> executor = new HibernateExecutor<Customer>();
//		Customer customer = new Customer();
//		customer.setName("MBB");
//		executor.executeCommand(new SaveCustomerCommand(customer));
	}
	@Test
	public void testSaveRequest(){
//		HibernateExecutor<Request> executor = new HibernateExecutor<Request>();
//		HibernateExecutor<List<Customer>> customerExecutor = new HibernateExecutor<List<Customer>>();
//		List<Customer> listCustomer = customerExecutor.executeCommand(new ListCustomersCommand());
//		Assert.assertFalse("Lista de clientes n�o pode ser vazia ", listCustomer.isEmpty());
//		
//		//Montar request false
//		Request request = new Request();
//		request.setCustomer(listCustomer.get(0));
//		request.setDescription("XXXXX");
//		request.setOpenDate(new Date());
//		request.setPrice(100.99);
//		request.setQuantity(102);
//		request.setStatus(RequestStatus.OPEN);
//		request.setPoCode("POCODE");
//		
//		Request retRequest = executor.executeCommand(new SaveRequestCommand(request));
//		Assert.assertNotNull("Request n�o pode ser nulo", retRequest);
//		Assert.assertEquals(request.getCustomer().getName(), retRequest.getCustomer().getName());
//		Assert.assertEquals(request.getDescription(), retRequest.getDescription());
//		Assert.assertEquals(request.getOpenDate(),retRequest.getOpenDate());
//		Assert.assertEquals(request.getPrice(), retRequest.getPrice());
//		Assert.assertEquals(request.getQuantity(),retRequest.getQuantity());
//		Assert.assertEquals(request.getStatus(),retRequest.getStatus());
//		Assert.assertEquals(request.getPoCode(),retRequest.getPoCode());
	}
	
}

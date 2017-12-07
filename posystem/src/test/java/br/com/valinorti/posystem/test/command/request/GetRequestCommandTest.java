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
public class GetRequestCommandTest extends BaseJUnitTest {


	@Test
	public void testGetCommand(){
//		HibernateExecutor<Customer> customerExecutor = new HibernateExecutor<Customer>();
//		Customer customer = new Customer();
//		customer.setName("MBB");
//		customer = customerExecutor.executeCommand(new SaveCustomerCommand(customer));
//		HibernateExecutor<Request> executor = new HibernateExecutor<Request>();
//		//Montar request false
//		Request request = new Request();
//		request.setCustomer(customer);
//		request.setDescription("XXXXX");
//		request.setOpenDate(new Date());
//		request.setPrice(100.99);
//		request.setQuantity(102);
//		request.setStatus(RequestStatus.OPEN);
//		request.setPoCode("CODE");
//		executor.executeCommand(new SaveRequestCommand(request));
//		
//		Filter filter = new Filter();
//		filter.setClazz(Request.class);
//		filter.addArgument(FilterConditions.EQUALS, "id", request.getId().toString(), ValueType.NUMBER);
//		
//		Request recRequest = executor.executeCommand(new GetRequestCommand(filter));
//		
//		Assert.assertNotNull(recRequest);
	}
	
}

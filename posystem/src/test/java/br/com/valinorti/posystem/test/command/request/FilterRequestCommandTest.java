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
public class FilterRequestCommandTest extends BaseJUnitTest {
	@Override
	public void prepare() throws Exception {
//		super.prepare();
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
//		request.setPoCode("00001");
//		executor.executeCommand(new SaveRequestCommand(request));
	}
	@Test
	public void testFilterRequest(){
//		HibernateExecutor<List<Request>> executor = new HibernateExecutor<List<Request>>();
//		Filter filter = new Filter();
//		filter.setClazz(Request.class);
//		filter.addArgument(FilterConditions.EQUALS, "description", "XXXXX", ValueType.STRING);
//		filter.addArgument(FilterConditions.EQUALS, "price", "100.99", ValueType.NUMBER);
//		filter.addArgument(FilterConditions.EQUALS, "quantity", "102", ValueType.NUMBER);
//		filter.addArgument(FilterConditions.EQUALS, "status", "0", ValueType.NUMBER);
//		List<Request> requestList = executor.executeCommand(new FilterRequestCommand(filter));
//		Assert.assertNotNull(requestList);
//		Assert.assertFalse(requestList.isEmpty());
	}
	@Test
	public void testFilterRequestForDateRange(){
//		HibernateExecutor<List<Request>> executor = new HibernateExecutor<List<Request>>();
//		Filter filter = new Filter();
//		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
//		Calendar calendar = Calendar.getInstance();
//		calendar.set(Calendar.DAY_OF_MONTH, 1);
//		Date startDate = calendar.getTime();
//		calendar.set(Calendar.DAY_OF_MONTH, 30);
//		Date endDate = calendar.getTime();
//		
//		filter.setClazz(Request.class);
//		filter.addArgument(FilterConditions.BETWEEN, "openDate"
//						, new String[]{df.format(startDate),df.format(endDate)}, ValueType.DATE);
//		List<Request> requestList = executor.executeCommand(new FilterRequestCommand(filter));
//		Assert.assertNotNull(requestList);
//		Assert.assertFalse(requestList.isEmpty());
	}	
}

/**
 * 
 */
package br.valinorti.posystem.service.view.validator.customer;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.FilterCustomerCommand;
import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerStatus;

/**
 * @author leafar
 *
 */
public class CustomerDocumentDBValidator implements
		ConstraintValidator<CustomerDocumentOnDB, String> {
	
	private CustomerDocumentOnDB field;

	
	public void initialize(CustomerDocumentOnDB customerDocument) {
		this.field = customerDocument;
	}

	public boolean isValid(String docValue, ConstraintValidatorContext arg1) {
		if (StringUtils.isBlank(docValue)) {
			return false;
		}
		//Checar se existe no banco de dados
		Filter filter = new Filter();
		filter.setClazz(Customer.class);
		filter.addArgument(FilterConditions.EQUALS, this.field.field(), docValue);
		filter.addArgument(FilterConditions.EQUALS, "status", CustomerStatus.ACTIVE);
		HibernateExecutor<List<Customer>> executor = new HibernateExecutor<List<Customer>>();
		List<Customer> filterResults = executor.executeCommand(new FilterCustomerCommand(filter));
		return filterResults.isEmpty();
	}

}

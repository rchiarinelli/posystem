/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.Customer;

/**
 * @author Rafael Chiarinelli
 *
 */
public class UpdateCustomerCommand implements HibernateCommand<Customer> {

	private Customer customer;
	
	
	
	/**
	 * @param customer
	 */
	public UpdateCustomerCommand(Customer customer) {
		this.customer = customer;
	}



	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public Customer execute(Session session) {
		session.saveOrUpdate(this.customer);
		return this.customer;
	}

}

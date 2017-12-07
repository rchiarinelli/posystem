/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.exception.POSystemException;
import br.valinorti.posystem.entity.Customer;

/**
 * @author Rafael Chiarinelli
 *
 */
public class SaveCustomerCommand implements HibernateCommand<Customer> {
	
	private Customer customer;
	
	public SaveCustomerCommand(Customer cust){
		this.customer = cust;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public Customer execute(Session session) {
		try {
			session.save(this.customer);
		} catch (HibernateException he) {
			throw new POSystemException(he.getMessage());
		}
		
		return this.customer;
	}

}

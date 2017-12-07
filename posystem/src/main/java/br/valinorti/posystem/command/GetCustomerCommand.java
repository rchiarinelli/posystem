/**
 * 
 */
package br.valinorti.posystem.command;



import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.HqlFilterEngine;
import br.valinorti.posystem.entity.Customer;

/**
 * @author Rafael Chiarinelli
 *
 */
public class GetCustomerCommand implements HibernateCommand<Customer> {

	private Filter filter;
	
	/**
	 * 
	 * @param filter
	 */
	public GetCustomerCommand(Filter filter) {
		super();
		this.filter = filter;
	}



	public Customer execute(Session session) {

		HqlFilterEngine engine = new HqlFilterEngine(session);
		Query query = engine.createQuery(filter);
		Customer customer = (Customer) query.uniqueResult();
		Hibernate.initialize(customer.getAddresses());
		Hibernate.initialize(customer.getContacts());
		return (Customer) query.uniqueResult();
	}

}

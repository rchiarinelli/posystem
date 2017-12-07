/**
 * 
 */
package br.valinorti.posystem.command;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.Customer;

/**
 * @author Rafael Chiarinelli
 *
 */
public class ListCustomersCommand implements HibernateCommand<List<Customer>> {

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> execute(Session session) {
		Query query =  session.createQuery("from Customer c where c.status=1");
		return query.list();
	}

}

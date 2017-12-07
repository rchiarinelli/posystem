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
 * @author leafar
 *
 */
public class ListCustomersWithBillingsCommand implements HibernateCommand<List<Customer>> {
	
	private Integer subscriberId;
	
	/**
	 * @param subscriberId
	 */
	public ListCustomersWithBillingsCommand(Integer subscriberId) {
		super();
		this.subscriberId = subscriberId;
	}

	/*
	 * (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> execute(Session session) {
		Query query = session.getNamedQuery("getCustomersWithBillings");
		query.setParameter("subscriberId", this.subscriberId);
		return query.list();
	}
}

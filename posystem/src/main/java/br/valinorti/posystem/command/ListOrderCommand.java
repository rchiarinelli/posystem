/**
 * 
 */
package br.valinorti.posystem.command;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.Order;

/**
 * @author Rafael Chiarinelli
 *
 */
public class ListOrderCommand implements HibernateCommand<List<Order>> {

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<Order> execute(Session param) {
		Query query = param.createQuery("from Order order");
		return query.list();
	}

}

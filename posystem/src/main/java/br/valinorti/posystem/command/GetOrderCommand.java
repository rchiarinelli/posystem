/**
 * 
 */
package br.valinorti.posystem.command;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.HqlFilterEngine;
import br.valinorti.posystem.entity.Order;

/**
 * @author Rafael Chiarinelli
 *
 */
public class GetOrderCommand implements HibernateCommand<Order>{

	private Filter filter;
	
	/**
	 * @param filter
	 */
	public GetOrderCommand(Filter filter) {
		super();
		this.filter = filter;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public Order execute(Session session) {
		HqlFilterEngine filterEngine = new HqlFilterEngine(session);
		Query query = filterEngine.createQuery(this.filter);
		List<Order> results = query.list();
		Order request = null;
		if (!results.isEmpty()) {
			request = results.get(0);
			//Inicializar cole��es
			Hibernate.initialize(request.getProductionOrders());
		} 
		return request;
	}
}

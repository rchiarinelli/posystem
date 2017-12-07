/**
 * 
 */
package br.valinorti.posystem.command;

import java.util.List;

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
public class FilterOrderCommand implements HibernateCommand<List<Order>> {

	private Filter filter;
	
	public FilterOrderCommand(Filter flt){
		this.filter = flt;
	}
	
	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<Order> execute(Session session) {
		//Montar parametros a partir dos argumentos passados
		HqlFilterEngine filterEngine = new HqlFilterEngine(session);
		Query query = filterEngine.createQuery(this.filter);
		return query.list();
	}

}

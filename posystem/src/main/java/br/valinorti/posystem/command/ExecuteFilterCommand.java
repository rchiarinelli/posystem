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

/**
 * @author leafar
 *
 */
public class ExecuteFilterCommand<T> implements HibernateCommand<List<T>> {

	private Filter filter;
	
	/**
	 * @param filter
	 */
	public ExecuteFilterCommand(Filter filter) {
		super();
		this.filter = filter;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	public List<T> execute(Session session) {
		HqlFilterEngine filterEngine = new HqlFilterEngine(session);
		Query query = filterEngine.createQuery(this.filter);
		return query.list();
	}
}

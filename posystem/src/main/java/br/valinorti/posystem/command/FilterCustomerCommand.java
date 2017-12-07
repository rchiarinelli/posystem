package br.valinorti.posystem.command;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.HqlFilterEngine;
import br.valinorti.posystem.entity.Customer;

public class FilterCustomerCommand implements HibernateCommand<List<Customer>> {

	private Filter filter;
	
	
	
	public FilterCustomerCommand(Filter filter) {
		super();
		this.filter = filter;
	}



	@SuppressWarnings("unchecked")
	public List<Customer> execute(Session session) {
		HqlFilterEngine filterEngine = new HqlFilterEngine(session);
		Query query = filterEngine.createQuery(this.filter);
		return query.list();
	}
}

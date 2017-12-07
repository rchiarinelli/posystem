/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.Order;

/**
 * @author rchiari
 *
 */
public class UpdateOrderCommand implements HibernateCommand<Order> {
	
	private Order request;
	
	
	/**
	 * 
	 * @param request
	 */
	public UpdateOrderCommand(Order request) {
		super();
		this.request = request;
	}



	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public Order execute(Session session) {
		session.update(this.request);
		return this.request;
	}
}

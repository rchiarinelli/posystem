/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.Order;

/**
 * @author Rafael Chiarinelli
 *
 */
public class SaveOrderCommand implements HibernateCommand<Order> {

	private Order request;
	
	public SaveOrderCommand(Order req) {
		this.request = req;
	}
	
	
	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public Order execute(Session session) {
		
		session.save(this.request);
		
		return this.request;
	}

}

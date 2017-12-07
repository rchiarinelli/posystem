/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.ProductionOrder;

/**
 * @author Rafael Chiarinelli
 *
 */
public class UpdatePOCommand implements HibernateCommand<ProductionOrder> {

	private ProductionOrder po;
	/**
	 * @param po
	 */
	public UpdatePOCommand(ProductionOrder po) {
		super();
		this.po = po;
	}
	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public ProductionOrder execute(Session session) {
		session.update(this.po);
		return this.po;
	}
}
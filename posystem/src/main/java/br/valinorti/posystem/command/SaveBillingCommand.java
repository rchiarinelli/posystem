/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.Billing;

/**
 * @author leafar
 *
 */
public class SaveBillingCommand implements HibernateCommand<Billing> {

	private Billing billing;

	/**
	 * @param billing
	 */
	public SaveBillingCommand(Billing billing) {
		super();
		this.billing = billing;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public Billing execute(Session session) {
		session.saveOrUpdate(this.billing);
		return this.billing;
	}
}

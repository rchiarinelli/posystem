/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.Billing;

/**
 * @author leafar
 *
 */
public class GetBillingByIdCommand implements HibernateCommand<Billing> {

	private Long billingId;
	
	/**
	 * @param billingId
	 */
	public GetBillingByIdCommand(Long billingId) {
		super();
		this.billingId = billingId;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public Billing execute(Session session) {
		Billing billing = (Billing) session.get(Billing.class, this.billingId);
		Hibernate.initialize(billing.getInstallments());
		return billing;
	}
}

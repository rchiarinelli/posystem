/**
 * 
 */
package br.valinorti.posystem.service;

import java.util.Set;

import br.valinorti.executor.HibernateExecutor;
import br.valinorti.posystem.command.GetBillingByIdCommand;
import br.valinorti.posystem.command.SaveBillingCommand;
import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.entity.BillingStatus;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;

/**
 * @author leafar
 *
 */
@SuppressWarnings("unchecked")
public class BillingFacade {

	@SuppressWarnings("rawtypes")
	private HibernateExecutor hibernateExecutor;
	
	/**
	 * @param hibernateExecutor
	 */
	@SuppressWarnings("rawtypes")
	public BillingFacade(HibernateExecutor hibernateExecutor) {
		super();
		this.hibernateExecutor = hibernateExecutor;
	}

	/**
	 * 
	 * @param billing
	 */

	public Billing updateBillingStatus(final Billing  billing) {
		Billing bean = (Billing) this.hibernateExecutor.executeCommand(new GetBillingByIdCommand(billing.getId()));
		//Caso o status for CLOSED
		if (bean.getStatus().equals(BillingStatus.OPEN)) {
			Set<Installment> installments =  bean.getInstallments();
			boolean hasPending = false;
			//Percorrer o set de parcelas para verificar se tem alguma parcela pendente
			for (Installment installment : installments) {
				if (installment.getStatus().equals(InstallmentStatus.PENDING)) {
					hasPending = true;
					break;
				}
			}
			//Caso nao tenha nenhuma pendencia
			if (!hasPending) {
				//Atualizar o status do Billing para Closed
				bean.setStatus(BillingStatus.CLOSED);
				bean = (Billing) this.hibernateExecutor.executeCommand(new SaveBillingCommand(bean));
			}
		}
		return bean;
	}
	
}

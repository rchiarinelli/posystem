/**
 * 
 */
package br.valinorti.posystem.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import br.valinorti.executor.HibernateExecutor;
import br.valinorti.filterengine.Filter;
import br.valinorti.filterengine.FilterConditions;
import br.valinorti.posystem.command.ExecuteFilterCommand;
import br.valinorti.posystem.command.SavePersistentEntityCommand;
import br.valinorti.posystem.entity.Billing;
import br.valinorti.posystem.entity.Installment;
import br.valinorti.posystem.entity.InstallmentStatus;
import br.valinorti.util.POSystemUtils;

/**
 * @author rchiari
 *
 */

public class InstallmentService  {

	
	/**
	 * Recupera uma lista de parcelas pendentes, no intervalo da data atual até 
	 * a quantidade de dias passados. 
	 * 
	 * @param days quantidade de dias a partir da data atual
	 * @return lista de parcelas pendentes
	 */
	public List<Installment> retrievePendingInstallments(int days) {
		//Construir o filtro, para todas as parcelas pendentes, 
		//nos proximos x dias passados via argumento do metodo
		Filter filter = new Filter();
		filter.setClazz(Installment.class);
		Date currentDate = new Date();
		
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.setTime(currentDate);
		calendar.add(Calendar.DAY_OF_YEAR, days);
		
		Date targetDate = calendar.getTime();
		
		filter.addArgument(FilterConditions.EQUALS, "status", InstallmentStatus.PENDING);
		filter.addArgument(FilterConditions.BETWEEN, "date", new Date[] {
				currentDate, targetDate });
		filter.addOrderBy(new String[]{"date"},Filter.OrderByType.ASCENDING);
		
		HibernateExecutor<List<Installment>> filterExecutor = new HibernateExecutor<List<Installment>>();
		List<Installment> instalments = filterExecutor.executeCommand(new ExecuteFilterCommand<Installment>(filter));
		return instalments;
	}
	/**
	 * Return the installment by the provided id.
	 * 
	 * @param id
	 * @return
	 */
	public Installment getById(Long id) {
		Filter filter = new Filter();
		filter.setClazz(Installment.class);
		filter.addArgument(FilterConditions.EQUALS, "id", id);
		HibernateExecutor<List<Installment>> filterExecutor = new HibernateExecutor<List<Installment>>();
		List<Installment> results = filterExecutor.executeCommand(new ExecuteFilterCommand<Installment>(filter));
		return results.get(0);
	}
	/**
	 * 
	 * @param installment
	 * @param paymentDate
	 * @return
	 */
	public Installment payInstallment(final Installment installment) {
		//Caso o sistema esteja configurado para usar juros no pagamento
		if (POSystemUtils.useInterest()) {
			//TODO condificar checagem de juros.
			Date expirationDate = installment.getDate();
			Date paymentDate = installment.getPaymentDate();
			
			//Checar se a data de pagamento é até a data de vencimento da parcela
			if (paymentDate.compareTo(expirationDate)>0) {
				//Caso for depois da data de vencimento, chegar se o valor informado é maior que 
			}
		}
		HibernateExecutor<Installment> executor = new HibernateExecutor<Installment>();
		Installment updatedInstallment = executor.executeCommand(new SavePersistentEntityCommand<Installment>(installment));
		//Atualizar o status da fatura
		BillingFacade facade = new BillingFacade(new HibernateExecutor<Billing>());
		facade.updateBillingStatus(updatedInstallment.getBilling());
		
		return updatedInstallment;
	}
}

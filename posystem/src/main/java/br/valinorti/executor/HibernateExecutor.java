/**
 * 
 */
package br.valinorti.executor;


import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import br.valinorti.command.Command;
import br.valinorti.exception.POSystemException;
import br.valinorti.util.HibernateUtils;

/**
 * @author rchiari
 * 
 */
public class HibernateExecutor<R> implements Executor<R, Session> {
	
	private static Logger logger = Logger.getLogger(HibernateExecutor.class);
	
	/**
	 * 
	 */
	public R executeCommand(Command<Session, R> command) {
		
		logger.debug("Executing command.");
		try {
			//Verificar se � um command para transacao
			R retValue = null;
			HibernateUtils.getSessionFactory().getCurrentSession().beginTransaction();
			retValue = command.execute(HibernateUtils.getSessionFactory().getCurrentSession());
			HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().commit();
			return retValue;
		} catch (HibernateException he) {
			logger.debug("Rolling back transaction ");
			HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().rollback();
			logger.error("An exception on persistence has occurred...: " + he);
			throw new POSystemException(he);
		} catch (Exception exc) {
			logger.debug("Rolling back transaction ");
			HibernateUtils.getSessionFactory().getCurrentSession().getTransaction().rollback();
			logger.error("An general exception has occurred...: " + exc);
			throw new POSystemException(exc);
		} 
	}
}

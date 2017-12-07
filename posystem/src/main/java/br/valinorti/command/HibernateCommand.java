/**
 * 
 */
package br.valinorti.command;

import org.hibernate.Session;

/**
 * @author rchiari
 *
 */
public interface HibernateCommand<R> extends Command<Session, R> {
	
	
	
}

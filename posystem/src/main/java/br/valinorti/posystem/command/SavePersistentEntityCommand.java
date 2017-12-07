/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;

/**
 * @author leafar
 *
 */
public class SavePersistentEntityCommand<R> implements HibernateCommand<R> {

	private R entity;
	
	/**
	 * @param entity
	 */
	public SavePersistentEntityCommand(R entity) {
		super();
		this.entity = entity;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	public R execute(Session session) {
		session.saveOrUpdate(this.entity);
		return this.entity;
	}

}

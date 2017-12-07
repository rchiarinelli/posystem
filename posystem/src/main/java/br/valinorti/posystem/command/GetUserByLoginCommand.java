/**
 * 
 */
package br.valinorti.posystem.command;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import br.valinorti.command.HibernateCommand;
import br.valinorti.posystem.entity.User;

/**
 * @author leafar
 *
 */
public class GetUserByLoginCommand implements HibernateCommand<User> {

	private String userLogin;
	
	/**
	 * @param userLogin
	 */
	public GetUserByLoginCommand(String userLogin) {
		super();
		this.userLogin = userLogin;
	}

	/* (non-Javadoc)
	 * @see br.valinorti.command.Command#execute(java.lang.Object)
	 */
	@Override
	public User execute(Session session) {
		Query query = session.createQuery("from User u where u.login=:userLogin");
		query.setParameter("userLogin", this.userLogin);
		User user = (User) query.uniqueResult();
		Hibernate.initialize(user.getPermissions());
		return user;
	}

}

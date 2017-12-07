/**
 * 
 */
package br.com.valinorti.posystem.test;

import org.junit.Before;

import br.valinorti.util.HibernateUtils;

/**
 * @author Rafael Chiarinelli
 *
 */
public abstract class BaseJUnitTest {
	
	@Before
	public void prepare() throws Exception {
		HibernateUtils.initSessionFactory("hibernate_test.cfg.xml");
		TestDatabaseDataGenerator.generateDBData();
	}

}

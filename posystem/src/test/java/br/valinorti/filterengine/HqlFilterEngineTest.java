package br.valinorti.filterengine;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import br.valinorti.posystem.entity.Customer;
import br.valinorti.posystem.entity.CustomerStatus;
import br.valinorti.posystem.entity.Installment;

/**
 * Testes unit�rios da classe {@link HqlFilterEngineTest}.
 * 
 * @author mhnagaoka
 */
public class HqlFilterEngineTest {

	private Session session;

	private Query query;

	private HqlFilterEngine engine;

	/**
	 * Prepara ambiente.
	 */
	@Before
	public void setUp() {
		session = mock(Session.class);
		query = mock(Query.class);
		when(session.createQuery(anyString())).thenReturn(query);
		engine = new HqlFilterEngine(session);
	}

	/**
	 * Cria uma query sem argumentos especificados no filtro.
	 */
	@Test
	public void testNoArguments() {

		Filter f = new Filter();
		f.setClazz(Customer.class);
		Query q = engine.createQuery(f);

		assertEquals(query, q);
		ArgumentCaptor<String> cap = ArgumentCaptor.forClass(String.class);
		verify(session).createQuery(cap.capture());
		verify(query, never()).setParameter(anyString(), anyObject());
		verify(query, never()).setParameterList(anyString(),
				(Object[]) anyObject());
		assertEquals("from br.valinorti.posystem.entity.Customer obj",
				cap.getValue());
	}

	/**
	 * Cria uma query com argumento EQUALS.
	 * 
	 * @see FilterConditions#EQUALS
	 */
	@Test
	public void testEqualsArgument() {
		Filter f = new Filter();
		f.setClazz(Customer.class);
		f.addArgument(FilterConditions.EQUALS, "name", "johndoe");
		Query q = engine.createQuery(f);

		assertEquals(query, q);
		ArgumentCaptor<String> cap = ArgumentCaptor.forClass(String.class);
		verify(session).createQuery(cap.capture());
		verify(query).setParameter(eq(HqlFilterEngine.PARAM_PREFIX + "0"),
				eq("johndoe"));
		verify(query, never()).setParameterList(anyString(),
				(Object[]) anyObject());
		assertEquals(
				"from br.valinorti.posystem.entity.Customer obj where name = :param_0",
				cap.getValue());
	}

	/**
	 * Cria uma query com argumento NOT_EQUALS.
	 * 
	 * @see FilterConditions#NOT_EQUALS
	 */
	@Test
	public void testNotEqualsArgument() {
		Filter f = new Filter();
		f.setClazz(Customer.class);
		f.addArgument(FilterConditions.NOT_EQUALS, "name", "johndoe");
		Query q = engine.createQuery(f);

		assertEquals(query, q);
		ArgumentCaptor<String> cap = ArgumentCaptor.forClass(String.class);
		verify(session).createQuery(cap.capture());
		verify(query).setParameter(eq(HqlFilterEngine.PARAM_PREFIX + "0"),
				eq("johndoe"));
		verify(query, never()).setParameterList(anyString(),
				(Object[]) anyObject());
		assertEquals(
				"from br.valinorti.posystem.entity.Customer obj where name <> :param_0",
				cap.getValue());
	}

	/**
	 * Cria uma query com argumento LIKE.
	 * 
	 * @see FilterConditions#LIKE
	 */
	@Test
	public void testLikeArgument() {
		Filter f = new Filter();
		f.setClazz(Customer.class);
		f.addArgument(FilterConditions.LIKE, "name", "%johndoe%");
		Query q = engine.createQuery(f);

		assertEquals(query, q);
		ArgumentCaptor<String> cap = ArgumentCaptor.forClass(String.class);
		verify(session).createQuery(cap.capture());
		verify(query).setParameter(eq(HqlFilterEngine.PARAM_PREFIX + "0"),
				eq("%johndoe%"));
		verify(query, never()).setParameterList(anyString(),
				(Object[]) anyObject());
		assertEquals(
				"from br.valinorti.posystem.entity.Customer obj where name like :param_0",
				cap.getValue());
	}

	/**
	 * Cria uma query com argumento BETWEEN.
	 * 
	 * @see FilterConditions#BETWEEN
	 */
	@Test
	public void testBetweenArgument() {

		Calendar cal1 = new GregorianCalendar(2011, Calendar.JANUARY, 10);
		Calendar cal2 = new GregorianCalendar(2011, Calendar.JANUARY, 15);

		Filter f = new Filter();
		f.setClazz(Installment.class);
		f.addArgument(FilterConditions.BETWEEN, "date",
				new Object[] { cal1.getTime(), cal2.getTime() });
		Query q = engine.createQuery(f);

		assertEquals(query, q);
		ArgumentCaptor<String> cap = ArgumentCaptor.forClass(String.class);
		verify(session).createQuery(cap.capture());
		verify(query).setParameter(eq(HqlFilterEngine.PARAM_PREFIX + "0"),
				eq(cal1.getTime()));
		verify(query).setParameter(eq(HqlFilterEngine.PARAM_PREFIX + "1"),
				eq(cal2.getTime()));
		verify(query, never()).setParameterList(anyString(),
				(Object[]) anyObject());
		assertEquals(
				"from br.valinorti.posystem.entity.Installment obj where date between :param_0 AND :param_1",
				cap.getValue());
	}

	/**
	 * Tenta criar uma query com argumento BETWEEN, mas especificando apenas um
	 * valor para fitragem (BETWEEN requer dois valores).
	 * 
	 * @see FilterConditions#BETWEEN
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testBetweenArgumentFail() {

		Calendar cal1 = new GregorianCalendar(2011, Calendar.JANUARY, 10);

		Filter f = new Filter();
		f.setClazz(Installment.class);
		f.addArgument(FilterConditions.BETWEEN, "date", cal1.getTime());

		engine.createQuery(f);
		fail();
	}

	/**
	 * Cria uma query com argumento IN.
	 * 
	 * @see FilterConditions#IN
	 */
	@Test
	public void testInArgument() {
		Filter f = new Filter();
		f.setClazz(Customer.class);
		f.addArgument(FilterConditions.IN, "status",
				new Object[] { CustomerStatus.ACTIVE.ordinal(),
						CustomerStatus.INACTIVE.ordinal() });
		Query q = engine.createQuery(f);

		assertEquals(query, q);
		ArgumentCaptor<String> hql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> inArgs = ArgumentCaptor
				.forClass(Object[].class);
		verify(session).createQuery(hql.capture());
		verify(query).setParameterList(eq(HqlFilterEngine.PARAM_PREFIX + "0"),
				(Object[]) inArgs.capture());
		verify(query, never()).setParameter(anyString(), anyObject());
		assertEquals(
				"from br.valinorti.posystem.entity.Customer obj where status in (:param_0)",
				hql.getValue());
		assertEquals(CustomerStatus.ACTIVE.ordinal(), inArgs.getValue()[0]);
		assertEquals(CustomerStatus.INACTIVE.ordinal(), inArgs.getValue()[1]);
	}

	/**
	 * Cria uma query com m�ltiplos argumentos.
	 */
	@Test
	public void testMultipleArguments() {
		Filter f = new Filter();
		f.setClazz(Customer.class);
		f.addArgument(FilterConditions.NOT_EQUALS, "name", "johndoe");
		f.addArgument(FilterConditions.LIKE, "details", "%supplier%");
		f.addArgument(FilterConditions.IN, "status",
				new Object[] { CustomerStatus.ACTIVE.ordinal(),
						CustomerStatus.INACTIVE.ordinal() });
		Query q = engine.createQuery(f);

		assertEquals(query, q);
		ArgumentCaptor<String> hql = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<Object[]> inArgs = ArgumentCaptor
				.forClass(Object[].class);
		verify(session).createQuery(hql.capture());
		verify(query).setParameter(eq(HqlFilterEngine.PARAM_PREFIX + "0"),
				eq("johndoe"));
		verify(query).setParameter(eq(HqlFilterEngine.PARAM_PREFIX + "1"),
				eq("%supplier%"));
		verify(query).setParameterList(eq(HqlFilterEngine.PARAM_PREFIX + "2"),
				(Object[]) inArgs.capture());
		assertEquals(
				"from br.valinorti.posystem.entity.Customer obj where name <> :param_0"
						+ " AND details like :param_1 AND status in (:param_2)",
				hql.getValue());
		assertEquals(CustomerStatus.ACTIVE.ordinal(), inArgs.getValue()[0]);
		assertEquals(CustomerStatus.INACTIVE.ordinal(), inArgs.getValue()[1]);
	}
}

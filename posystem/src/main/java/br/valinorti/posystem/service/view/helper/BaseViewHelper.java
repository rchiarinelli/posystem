/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.WrapDynaBean;
import org.apache.log4j.Logger;

import br.valinorti.exception.POSystemException;

/**
 * Helper base com metodos de uso geral para os helpers.
 * 
 * @author leafar
 *
 */
public abstract class BaseViewHelper<V,E> {
	
	private static Logger logger = Logger.getLogger(BaseViewHelper.class);

	/**
	 * Gera uma nova instancia de V.
	 * @return
	 */
	protected abstract V createViewInstance();
	/**
	 * Gera uma nova instancia de E.
	 * @return
	 */
	protected abstract E createEntityInstance();
	
	/**
	 * Monta um novo object <T>, baseado no srcBean. Os dados
	 * alterados são passado no targetBean.
	 * 
	 * @param srcBean
	 * @param targetBean
	 * @return um objeto contento as alteracoes e os dados que nao foram alterados
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public synchronized E generateEntityWithChanges(final E srcBean,E targetBean) {
		E beanChanges = null;
		try {
			beanChanges = (E) BeanUtilsBean.getInstance().cloneBean(srcBean);
			WrapDynaBean dynaBean = new WrapDynaBean(targetBean);
			Map props = BeanUtilsBean.getInstance().describe(targetBean);
			Map<String,Object> populatedProps = new HashMap<String,Object>();
			Set keys = props.keySet();
			Object value = null;
			for (Object key : keys) {
				value = dynaBean.get(key.toString());
				if (value!=null) {
					populatedProps.put(key.toString(), value);
				}
			}
			BeanUtilsBean.getInstance().populate(beanChanges, populatedProps);
		} catch (NoSuchMethodException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		} catch (InvocationTargetException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		} catch (InstantiationException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		} catch (IllegalAccessException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		}
		return beanChanges;
	}
	/**
	 * 
	 * @param source
	 * @return
	 */
	public synchronized E convertViewBeanToEntity(V view) {
		E entity = null;
		try {
			entity = this.createEntityInstance();
			BeanUtilsBean.getInstance().copyProperties(entity, view);
		} catch (InvocationTargetException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		} catch (IllegalAccessException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		}
		return entity;
	}
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public synchronized V convertEntityToViewBean(E entity) {
		V view = null;
		try {
			view = this.createViewInstance();
			BeanUtilsBean.getInstance().copyProperties(view, entity);
		} catch (InvocationTargetException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		} catch (IllegalAccessException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		}
		return view;
	}
	
	/**
	 * 
	 * @param entityList
	 * @return
	 */
	public synchronized List<V> convertEntityListToViewList(List<E> entityList) {
		List<V> viewList = new ArrayList<V>();
		for (E e : entityList) {
			viewList.add(convertEntityToViewBean(e));
		}
		return viewList;
	}
	
	
	/**
	 * 
	 * @param viewbeanList
	 * @return
	 */
	public synchronized List<E> convertViewBeanListToEntityList(List<V> viewbeanList) {
		List<E> entityList = new ArrayList<E>();
		for (V v : viewbeanList) {
			entityList.add(convertViewBeanToEntity(v));
		}
		return entityList;
	}
	
}

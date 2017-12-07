/**
 * 
 */
package br.valinorti.posystem.service.view.helper;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import br.valinorti.exception.POSystemException;
import br.valinorti.posystem.service.view.ViewBean;

/**
 * Classe utilitaria para converter classes {@link ViewBean}, diretamente
 * para classes JSON. Usada principalmente quando é preciso gerar respostas
 * JSON para os componentes DOJO.
 * 
 * @author Rafael Chiarinelli
 *
 */
public class JSONViewHelper {
	
	private static Logger logger = Logger.getLogger(JSONViewHelper.class);
	/**
	 * Converte um {@link List} de ViewBeans, para um JSON com um array, no seguinte formato:
	 * {"items":[key1:value1,key2:value2....]}.
	 * @param items
	 * @return
	 */
	public synchronized JSONObject convertBeanListToJSONGridItems(List<? extends ViewBean> items){
		JSONObject jsonResponse = null;
		try {
			jsonResponse = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			JSONObject entry = null;
			for (ViewBean viewBean : items) {
				entry = this.convertBeanToJSON(viewBean);
				jsonArray.put(entry);
			}
			jsonResponse.put("items", jsonArray);
		} catch (JSONException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		}
		return jsonResponse;
	}
	
	/**
	 * Converte o {@link ViewBean} passado para um objeto JSON, no seguinte formato:
	 * {key1:value1,key2:value2...}
	 * 
	 * @param items
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized JSONObject convertBeanToJSON(ViewBean item){
		JSONObject jsonResponse = null;
		try {
			//recuperar os getter da classe
			jsonResponse = new JSONObject();
			Map<String,String> beanDescription = BeanUtils.describe(item);
			Set<String> keys = beanDescription.keySet();
			for (String k : keys) {
				if (!k.equals("class")) {
					jsonResponse.put(k
								, beanDescription.get(k));
				}
			}
		} catch (JSONException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		} catch (Exception exc) {
			logger.equals(exc);
			throw new POSystemException(exc);
		}
		return jsonResponse;
	}
	
	/**
	 * Converte o Object passado para um objeto JSON, no seguinte formato:
	 * {key1:value1,key2:value2...}
	 * 
	 * @param items
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public synchronized JSONObject convertBeanToJSON(Object item){
		JSONObject jsonResponse = null;
		try {
			//recuperar os getter da classe
			jsonResponse = new JSONObject();
			Map<String,String> beanDescription = BeanUtils.describe(item);
			Set<String> keys = beanDescription.keySet();
			for (String k : keys) {
				if (!k.equals("class") && k.contains("get")) {
					jsonResponse.put(k
								, beanDescription.get(k));
				}
			}
		} catch (JSONException exc) {
			logger.error(exc);
			throw new POSystemException(exc);
		} catch (Exception exc) {
			logger.equals(exc);
			throw new POSystemException(exc);
		}
		return jsonResponse;
	}
	

	
	
}

/**
 * 
 */
package br.valinorti.util;

import java.util.ResourceBundle;

import br.valinorti.exception.POSystemException;

/**
 * Class utilitaria para recuperar as mensagens e configura&ccedil;&otilde;es
 * do sistema. 
 * 
 * @author Rafael Chiarinelli
 *
 */
public class SystemMessages {
	
	private static final ResourceBundle bundle;
	
	static {
		bundle = ResourceBundle.getBundle("system");
	}
	
	private SystemMessages(){
		
	}
	/**
	 * Recupera a mensagem da chave passada.
	 * @param key
	 * @return
	 */
	public synchronized static String getMessage(String key){
		String message = bundle.getString(key);
		if (message==null) {
			throw new POSystemException("Propriedade " + key + " n&atilde;o encontrada.");
		}
		return message;
	}

	/**
	 * Recupera o valor int da chave passada.
	 * @param key
	 * @return
	 */
	public synchronized static int getIntValue(String key){
		String message = getMessage(key);
		return Integer.parseInt(message);
	}
	
}

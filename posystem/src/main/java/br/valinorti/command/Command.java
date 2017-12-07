/**
 * 
 */
package br.valinorti.command;

/**
 * Interface que define o contrato base para os
 * commands.
 * 
 * @author Rafael Chiarinelli
 *
 */
public interface Command<P, R> {
	/**
	 * 
	 * @param param
	 * @return
	 */
	public R execute(P param);
	
}

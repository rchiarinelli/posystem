/**
 * 
 */
package br.valinorti.executor;

import br.valinorti.command.Command;

/**
 * @author rchiari
 *
 */
public interface Executor<R,P> {
	/**
	 * 
	 * @param command
	 * @return
	 */
	public R executeCommand(Command<P,R> command);
	
}

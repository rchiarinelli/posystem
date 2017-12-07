package br.valinorti.exception;

import java.util.List;

import br.valinorti.util.SystemMessages;
/**
 * 
 */

/**
 * @author Rafael Chiarinelli
 *
 */
public class POSystemException extends RuntimeException {

	private String systemMessages;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4640465116925478625L;

	/**
	 * 
	 */
	public POSystemException() {

	}

	/**
	 * @param message
	 */
	public POSystemException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public POSystemException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public POSystemException(String message, Throwable cause) {
		super(message, cause);
	}
	/**
	 * 
	 * @param messagesList
	 */
	public POSystemException(List<String> messagesList){
		StringBuilder messages = new StringBuilder();
		for (String msg : messagesList) {
			messages.append(msg);
			messages.append(SystemMessages.getMessage("response_line_break"));
		}
		this.systemMessages = messages.toString();
	}

	public String getSystemMessages() {
		if (this.systemMessages==null || this.systemMessages.isEmpty()) {
			return this.getMessage();
		}
		return systemMessages;
	}
}

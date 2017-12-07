/**
 * 
 */
package br.valinorti.posystem.report.util;

import java.io.OutputStream;
import java.sql.Connection;
import java.util.Map;

/**
 * @author leafar
 *
 */
public class ReportParams {
	
	private String path;
	
	private OutputStream output;
	
	private Map<String, String> params;
	
	private Connection connection;
	
	
	
	/**
	 * 
	 */
	public ReportParams() {
		super();
	}

	/**
	 * @param path
	 * @param output
	 * @param params
	 * @param connection
	 */
	public ReportParams(String path, OutputStream output,
			Map<String, String> params, Connection connection) {
		super();
		this.path = path;
		this.output = output;
		this.params = params;
		this.connection = connection;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the output
	 */
	public OutputStream getOutput() {
		return output;
	}

	/**
	 * @param output the output to set
	 */
	public void setOutput(OutputStream output) {
		this.output = output;
	}

	/**
	 * @return the params
	 */
	public Map<String, String> getParams() {
		return params;
	}

	/**
	 * @param params the params to set
	 */
	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
}

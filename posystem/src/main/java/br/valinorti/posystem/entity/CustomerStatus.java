/**
 * 
 */
package br.valinorti.posystem.entity;

/**
 * @author Rafael Chiarinelli
 *
 */
public enum CustomerStatus {
	
	INACTIVE("Inativo")
	, ACTIVE("Ativo");
	
	private final String status;
	
	private CustomerStatus(String c){
		this.status = c;
	}
	
	public String getStatusValue(){
		return this.status;
	}
}

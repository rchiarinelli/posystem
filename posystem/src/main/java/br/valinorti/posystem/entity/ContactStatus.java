/**
 * 
 */
package br.valinorti.posystem.entity;

/**
 * Enum de status da entidade CustomerContact.
 * 
 * @author leafar
 *
 */
public enum ContactStatus {

	INACTIVE("Inativo")
	, ACTIVE("Ativo");
	
	private final String status;
	
	private ContactStatus(String c){
		this.status = c;
	}
	
	public String getStatusValue(){
		return this.status;
	}
	
}

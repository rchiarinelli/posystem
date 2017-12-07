/**
 * 
 */
package br.valinorti.posystem.entity;

/**
 * @author leafar
 *
 */
public enum PaymentType {

	INSTALLMENTS("Parcelado"),NO_INTALLMENTS("A vista");
	
	private final String type;
	
	private PaymentType(String t){
		this.type = t;
	}
	
	public String getTypeValue(){
		return this.type;
	}
	
}

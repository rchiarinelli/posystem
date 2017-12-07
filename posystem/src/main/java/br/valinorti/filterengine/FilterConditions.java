/**
 * 
 */
package br.valinorti.filterengine;

/**
 * @author Rafael Chiarinelli
 *
 */
public enum FilterConditions {

	EQUALS("="),NOT_EQUALS("<>"),LIKE("like"),BETWEEN("between"),IN("in");
	
	private final String cond;
	
	FilterConditions(String c){
		this.cond = c;
	}
	
	public String getArgValue(){
		return this.cond;
	}
	
}

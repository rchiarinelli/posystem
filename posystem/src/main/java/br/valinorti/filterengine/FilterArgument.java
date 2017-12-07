/**
 * 
 */
package br.valinorti.filterengine;

/**
 * Argumento de um filtro de objetos.
 * 
 * @author Rafael Chiarinelli
 * @author Mauricio Nagaoka
 */
public class FilterArgument {

	private FilterConditions condition;

	private String field;

	private Object[] values;

	/**
	 * Cria um argumento de filtro de objetos.
	 * 
	 * @param condition
	 *            condição a ser filtrada
	 * @param field
	 *            campo a ser filtrado
	 * @param values
	 *            valores do campo a serem filtrados
	 * @param valueType
	 *            tipo dos valores do campo
	 */
	@Deprecated
	public FilterArgument(FilterConditions condition, String field,
			Object[] values, ValueType valueType) {
		this.condition = condition;
		this.field = field;
		this.values = values;
	}

	/**
	 * Cria um argumento de filtro de objetos.
	 * 
	 * @param condition
	 *            condição a ser filtrada
	 * @param field
	 *            campo a ser filtrado
	 * @param values
	 *            valores do campo a serem filtrados
	 */
	public FilterArgument(FilterConditions condition, String field,
			Object[] values) {
		this.condition = condition;
		this.field = field;
		this.values = values;
	}

	/**
	 * @return the condition
	 */
	public FilterConditions getCondition() {
		return condition;
	}

	/**
	 * @return the field
	 */
	public String getField() {
		return field;
	}

	/**
	 * @return the values
	 */
	public Object[] getValues() {
		return values;
	}

	/**
	 * @return the valueType
	 */
	@Deprecated
	public ValueType getValueType() {
		throw new UnsupportedOperationException();
	}
}

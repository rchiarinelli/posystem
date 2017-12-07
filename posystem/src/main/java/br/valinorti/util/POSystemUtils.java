/**
 * 
 */
package br.valinorti.util;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;

/**
 * @author leafar
 *
 */
public final class POSystemUtils {

	private static Locale systemLocale;
	
	private static NumberFormat decimalFormat;
	
	private static DateFormat dateFormat;
	
	private static SimpleDateFormat dateFormatForFilter;
	
	static {

		String systemLanguage = SystemMessages.getMessage("system_locale_language");
		String systemCountry = SystemMessages.getMessage("system_locale_country");
		
		systemLocale = new Locale(systemLanguage, systemCountry);

		decimalFormat = DecimalFormat.getCurrencyInstance(getSystemLocale());
		decimalFormat.setCurrency(Currency.getInstance(getSystemLocale()));
		
		dateFormat = new SimpleDateFormat(SystemMessages.getMessage("date_pattern"));
		
		dateFormatForFilter = new SimpleDateFormat(SystemMessages.getMessage("filter_date_pattern"));
		
	}
	
	private POSystemUtils(){}

	
	/**
	 * Converte uma string que pode conter os seguintes documentos: cpf,rg,cnpj,cgc,
	 * inscricao estadual ou inscricao municipal, em um Long.
	 * 
	 * @param src
	 * @return
	 */
	public synchronized static Long convertIdentityValueStringToLong(String src) {
		String newValue = src.replace(".","").replace("-", "").replace("/", "");
		return Long.parseLong(newValue);
	}
	/**
	 * Retorna o system locale default do sistema, independente do sistema
	 * operacional corrente.
	 */
	public synchronized static Locale getSystemLocale() {
		return systemLocale;
	}
	/**
	 * Retorna um Double convertido para a moeda local, de acordo com
	 * o system locale.
	 * 
	 * @see getSystemLocale()
	 * @param decimalValue
	 * @return
	 */
	public synchronized static String getCurrencyValueAsString(Double decimalValue) {
		return decimalFormat.format(decimalValue);
	}
	/**
	 * Converte a string passada contendo um valor na moeda do locale corrente
	 * em um {@link Number}. 
	 * 
	 * @param value
	 * @return
	 */
	public synchronized static Number getCurrencyValue(String value) throws ParseException {
		return decimalFormat.parse(value);
	}
	/**
	 * Faz o parse de uma data a partir de um String para o objeto Date, de
	 * acordo com o formato padrao do sistema.
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public synchronized static Date parseDate(String date) throws ParseException {
		return dateFormat.parse(date);
	}
	
	/**
	 * Formata a data registrada no objeto {@link Date} em uma String, de acordo
	 * com o formato padrao do sistema.
	 * 
	 * @param date
	 * @return
	 */
	public synchronized static String formatDate(Date date) {
		return dateFormat.format(date);
	}
	/**
	 * Formata o data passada em uma String, de acordo com o formato usado
	 * para armazenar o tipo Date no banco de dados.
	 * 
	 * @param date
	 * @return
	 */
	public synchronized static String formatDateForFilter(Date date) {
		return dateFormatForFilter.format(date);
	}
	/**
	 * Retorna se o sistema está configurado para usar taxa de juros.
	 * @return
	 */
	public synchronized static boolean useInterest() {
		String value = SystemMessages.getMessage("billing_installments_use_interest");
		return Boolean.parseBoolean(value);
	}
	/**
	 * Retorna se a taxa de juros ao mes.
	 * 
	 * @return
	 */
	public synchronized static float getInterestTax() {
		String value = SystemMessages.getMessage("interest_tax_per_month");
		return Float.parseFloat(value);
	}
}

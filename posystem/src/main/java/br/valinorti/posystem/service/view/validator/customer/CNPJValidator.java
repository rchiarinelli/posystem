/**
 * 
 */
package br.valinorti.posystem.service.view.validator.customer;

import org.apache.commons.lang.StringUtils;

import br.valinorti.posystem.service.view.validator.Validator;

/**
 * @author Rafael Chiarinelli
 *
 */
public class CNPJValidator implements Validator {

	private String cnpj;
	
	
	
	public CNPJValidator(String cnpf) {
		super();
		this.cnpj = cnpf;
	}



	/* (non-Javadoc)
	 * 
	 * @see br.valinorti.posystem.service.view.validator.Validator#validate()
	 */
	public boolean validate() {
		if (StringUtils.isBlank(this.cnpj)) {
			return false;
		}
		String threatedValue = this.cnpj.replace(".", "").replace("/", "").replace("-", "");
		int soma = 0, dig;
		String cnpj_calc = threatedValue.substring(0, 12);

		if (threatedValue.length() != 14){
			return false;
		}

		char[] chr_cnpj = threatedValue.toCharArray();

		/* Primeira parte */
		for (int i = 0; i < 4; i++)
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
				soma += (chr_cnpj[i] - 48) * (6 - (i + 1));
		for (int i = 0; i < 8; i++)
			if (chr_cnpj[i + 4] - 48 >= 0 && chr_cnpj[i + 4] - 48 <= 9)
				soma += (chr_cnpj[i + 4] - 48) * (10 - (i + 1));
		dig = 11 - (soma % 11);

		cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

		/* Segunda parte */
		soma = 0;
		for (int i = 0; i < 5; i++)
			if (chr_cnpj[i] - 48 >= 0 && chr_cnpj[i] - 48 <= 9)
				soma += (chr_cnpj[i] - 48) * (7 - (i + 1));
		for (int i = 0; i < 8; i++)
			if (chr_cnpj[i + 5] - 48 >= 0 && chr_cnpj[i + 5] - 48 <= 9)
				soma += (chr_cnpj[i + 5] - 48) * (10 - (i + 1));
		dig = 11 - (soma % 11);
		cnpj_calc += (dig == 10 || dig == 11) ? "0" : Integer.toString(dig);

		return threatedValue.equals(cnpj_calc);
	}

}

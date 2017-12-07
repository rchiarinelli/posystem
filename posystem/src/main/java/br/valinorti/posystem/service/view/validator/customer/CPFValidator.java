package br.valinorti.posystem.service.view.validator.customer;

import org.apache.commons.lang.StringUtils;

import br.valinorti.posystem.service.view.validator.Validator;

/**
 * 
 * @author Rafael Chiarinelli
 * 
 */
public class CPFValidator implements Validator {

	private String cpf;

	/**
	 * 
	 * @param cpf
	 */
	public CPFValidator(String cpf) {
		super();
		this.cpf = cpf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.valinorti.posystem.service.view.validator.Validatorvalidate()
	 */
	public boolean validate() {
		if (StringUtils.isBlank(this.cpf)) {
			return false;
		}
		int resultP = 0;
		int resultS = 0;
		String threatedValue = this.cpf.replace(".", "").replace("/", "").replace("-", "");
		int[] cpf = new int[threatedValue.length()];

		char[] charArray = threatedValue.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			cpf[i] = Integer.parseInt(String.valueOf(charArray[i]));
		}

		// calcula o primeiro numero(DIV) do cpf
		for (int i = 0; i < 9; i++) {
			resultP += cpf[i] * (i + 1);
		}
		int divP = resultP % 11;

		// se o resultado for diferente ao 10 digito do cpf retorna falso
		if (divP != cpf[9]) {
			return false;
		} else {
			// calcula o segundo nmero(DIV) do cpf
			for (int i = 0; i < 10; i++) {
				resultS += cpf[i] * (i);
			}
			int divS = resultS % 11;

			// se o resultado for diferente ao 11 digito do cpf retorna falso
			if (divS != cpf[10]) {
				return false;
			}
		}

		// se tudo estiver ok retorna verdadeiro
		return true;
	}
}

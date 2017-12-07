/**
 * 
 */
package br.valinorti.posystem.service.view.validator.customer;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import br.valinorti.posystem.service.view.validator.Validator;

/**
 * @author Rafael Chiarinelli
 *
 */
public class CustomerDocumentValidator implements
		ConstraintValidator<CustomerDocument, String> {

	private CustomerDocumentType type;
	
	public void initialize(CustomerDocument customerDocument) {
		this.type = customerDocument.value();
		
	}
	public boolean isValid(String docValue, ConstraintValidatorContext arg1) {
		if (StringUtils.isBlank(docValue)) {
			return false;
		}
		Validator validator = null;
		if (type.equals(CustomerDocumentType.CNPJ)) {
			validator = new CNPJValidator(docValue.toString());
		} else if (type.equals(CustomerDocumentType.CPF)) {
			validator = new CPFValidator(docValue.toString());
		}
		return validator.validate();	
	}

}

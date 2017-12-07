/**
 * 
 */
package br.valinorti.posystem.service.view.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.HibernateValidator;

import br.valinorti.exception.POSystemException;
import br.valinorti.posystem.service.view.ViewBean;

/**
 * @author Rafael Chiarinelli
 *
 */
public class ViewBeanValidator {
	/**
	 * Valida um {@link ViewBean}. Em caso de erro de valida��o, esse m�todo
	 * lan�a um {@link POSystemException}, contendo uma lista de erros. Utiliza
	 * a api HibernateValidator para fazer a valida��o.
	 * 
	 * @param viewBean
	 */
	public void validate(ViewBean viewBean){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<ViewBean>> violations = validator.validate(viewBean);
        //Caso houverem violacoes de constraints, lan�ar exce��o
		if (violations != null && violations.size() > 0) {
			List<String> messageList = new ArrayList<String>();
			for (ConstraintViolation<ViewBean> violation : violations) {
				messageList.add(violation.getMessage());
			}
			throw new POSystemException(messageList);
		}
	}
	/**
	 * Valida um {@link Object}.Que esteja anotado com as validacoes 
	 * do {@link HibernateValidator}.
	 * 
	 * @param viewBean
	 */
	public void validate(Object obj){
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(obj);
        //Caso houverem violacoes de constraints, lan�ar exce��o
		if (violations != null && violations.size() > 0) {
			List<String> messageList = new ArrayList<String>();
			for (ConstraintViolation<Object> violation : violations) {
				messageList.add(violation.getMessage());
			}
			throw new POSystemException(messageList);
		}
	}	
}

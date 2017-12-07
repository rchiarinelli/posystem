/**
 * 
 */
package br.valinorti.posystem.service.view.validator.customer;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Anota��o usada para validar o conte�do de um campo com a identifica��o do cliente.
 * 
 * @author Rafael Chiarinelli
 *
 */
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomerDocumentValidator.class)
@Documented
public @interface CustomerDocument {

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
    String message();
    
    CustomerDocumentType value();
}

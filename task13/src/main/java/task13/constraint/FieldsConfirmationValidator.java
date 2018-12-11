package task14.constraint;

import org.springframework.beans.BeanWrapperImpl;
import task14.annotation.FieldsConfirmation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldsConfirmationValidator implements ConstraintValidator<FieldsConfirmation, Object> {

    private String field;
    private String fieldConfirmation;

    @Override
    public void initialize(FieldsConfirmation constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldConfirmation = constraintAnnotation.fieldConfirmation();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext context) {
        BeanWrapperImpl beanWrapper = new BeanWrapperImpl(object);
        Object fieldValue = beanWrapper.getPropertyValue(field);
        Object fieldConfirmationValue = beanWrapper.getPropertyValue(fieldConfirmation);
        if (fieldValue != null) {
            return fieldValue.equals(fieldConfirmationValue);
        } else {
            return fieldConfirmationValue == null;
        }
    }
}

package task14.annotation;

import task14.constraint.FieldsConfirmationValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldsConfirmationValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldsConfirmation {

    String message() default "Fields values do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    String fieldConfirmation();

    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        FieldsConfirmation[] value();
    }
}

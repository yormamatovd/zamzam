package email.annotations;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = Gmail.GmailValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Gmail {
    String message() default "Gmail(example@gmail.com) is mandatory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class GmailValidator implements ConstraintValidator<Gmail, String> {
        @Override
        public void initialize(Gmail constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;
            return value.toLowerCase().endsWith("@gmail.com") && value.length() > 11;
        }
    }
}

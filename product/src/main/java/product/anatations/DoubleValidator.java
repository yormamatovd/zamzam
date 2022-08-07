package product.anatations;

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
@Constraint(validatedBy = DoubleValidator.Validate.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface DoubleValidator {
    String message();
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validate implements ConstraintValidator<DoubleValidator, Double> {
        @Override
        public void initialize(DoubleValidator constraintAnnotation) {
        }

        @Override
        public boolean isValid(Double value, ConstraintValidatorContext context) {
            if (value == null) return false;
            return value >= 0;
        }
    }
}

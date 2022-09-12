package register.annotations;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = Name.NameValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Name {
    String message() default "Name(min_length=2,max_length=25, ALPHABETIC) is mandatory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class NameValidator implements ConstraintValidator<Name, String> {

        @Override
        public void initialize(Name constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;
            if (value.length() < 2 || value.length() > 25) return false;
            for (int i = 0; i < value.length(); i++) {
                if (Character.isDigit(value.charAt(i))) {
                    return false;
                }
            }
            return true;
        }
    }
}

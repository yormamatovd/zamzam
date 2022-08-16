package seller.annotations;

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
@Constraint(validatedBy = Password.PasswordValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Password {
    String message() default "Password(min_length=5,max_length=15, DIGITS AND ALPHABETIC) is mandatory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PasswordValidator implements ConstraintValidator<Password, String> {

        @Override
        public void initialize(Password constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;
            if (value.length() < 5 || value.length() > 15) return false;
            for (int i = 0; i < value.length(); i++) {
                if (Character.isDigit(value.charAt(i))) {
                    for (int i1 = 0; i1 < value.length(); i1++) {
                        if (Character.isAlphabetic(value.charAt(i1))) return true;
                    }
                }
            }
            return false;
        }
    }
}

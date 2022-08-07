package auth.annotations;

import auth.helper.Helper;

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
@Constraint(validatedBy = Username.UsernameValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Username {
    String message() default "Wrong username; Username must be phone(**1234567) or email(@gmail.com)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UsernameValidator implements ConstraintValidator<Username, String> {
        @Override
        public void initialize(Username constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;
            if (Helper.hasAlphabetic(value)) {
                return value.toLowerCase().endsWith("@gmail.com") && value.length() > 11;
            } else {
                return Helper.validPhone(value);
            }
        }
    }
}

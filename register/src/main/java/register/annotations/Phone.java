package register.annotations;

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
@Constraint(validatedBy = Phone.PhoneValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface Phone {
    String message() default "Phone(**1234567) is mandatory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class PhoneValidator implements ConstraintValidator<Phone, String> {
        @Override
        public void initialize(Phone constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;
            if (value.length() != 9) return false;
            String[] phonePrefix = new String[]{
                    "99",
                    "95",
                    "77",
                    "88",
                    "87",
                    "93",
                    "94",
                    "90",
                    "91",
            };
            for (String prefix : phonePrefix) {
                if (value.startsWith(prefix)) return true;
            }
            return false;
        }
    }
}

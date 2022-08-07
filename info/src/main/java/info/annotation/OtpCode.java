package info.annotation;

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
@Constraint(validatedBy = OtpCode.OtpCodeValidator.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface OtpCode {
    String message() default "OtpCode(length=6, DIGITS) is mandatory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class OtpCodeValidator implements ConstraintValidator<OtpCode, String> {

        @Override
        public void initialize(OtpCode constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;
            if (value.length() != 6) return false;
            for (int i = 0; i < value.length(); i++) {
                if (Character.isAlphabetic(value.charAt(i))) return false;
            }
            return true;
        }
    }
}

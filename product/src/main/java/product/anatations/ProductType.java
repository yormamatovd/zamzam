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
@Constraint(validatedBy = ProductType.Validate.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RUNTIME)
public @interface ProductType {
    String message() default "ProductType is mandatory";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validate implements ConstraintValidator<ProductType, String> {
        @Override
        public void initialize(ProductType constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (value == null) return false;
            for (product.enums.ProductType productType : product.enums.ProductType.values()) {
                if (value.contains(productType.name())) {
                    return true;
                }
            }
            return false;
        }
    }
}

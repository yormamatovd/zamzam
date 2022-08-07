package client.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;


@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class MethodArgumentNotValidExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Error> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        return processCustomFieldErrors(result.getFieldErrors());
    }

    private ResponseEntity<Error> processCustomFieldErrors(List<FieldError> fieldErrors) {
        Error error = new Error(HttpStatus.BAD_REQUEST.value(), "Validation error");
        for (FieldError fieldError : fieldErrors) {
            error.addFieldError(
                    fieldError.getObjectName(), fieldError.getField(), fieldError.getDefaultMessage()
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(error);
    }

    @Getter
    @Setter
    class Error {
        private final int status;
        private final String message;
        protected List<CustomFieldError> fieldErrors = new ArrayList<>();

        Error(int status, String message) {
            this.status = status;
            this.message = message;
        }

        public void addFieldError(String objectName, String fieId, String message) {
            CustomFieldError error = new CustomFieldError(objectName, fieId, message);
            fieldErrors.add(error);

        }
    }

    @Getter
    @Setter
    class CustomFieldError {
        private String objectName;
        private String fieId;
        private String message;

        public CustomFieldError(String objectName, String fieId, String message) {
            this.objectName = objectName;
            this.fieId = fieId;
            this.message = message;
        }
    }
}

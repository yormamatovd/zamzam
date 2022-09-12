package attachment.exceptions;

import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyExceptionHandler {


    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<String> badRequest(UnsupportedMediaTypeException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> badRequest(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<String> badRequest(FeignException.NotFound ex) {
        return new ResponseEntity<>("NOT FOUND", HttpStatus.NOT_FOUND);
    }
}

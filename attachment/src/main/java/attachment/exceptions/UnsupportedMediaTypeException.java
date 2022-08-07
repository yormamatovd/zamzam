package attachment.exceptions;

import attachment.enums.ApiStatus;

public class UnsupportedMediaTypeException extends RuntimeException{
    public UnsupportedMediaTypeException(ApiStatus status) {
        super(status.name());
    }
}

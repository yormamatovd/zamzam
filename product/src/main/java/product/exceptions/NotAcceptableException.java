package product.exceptions;

import product.enums.ApiStatus;

public class NotAcceptableException extends RuntimeException {
    public NotAcceptableException(ApiStatus status) {
        super(status.name());
    }

}

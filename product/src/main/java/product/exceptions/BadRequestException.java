package product.exceptions;

import product.enums.ApiStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(ApiStatus status) {
        super(status.name());
    }
}

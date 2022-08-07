package product.exceptions;

import product.enums.ApiStatus;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}

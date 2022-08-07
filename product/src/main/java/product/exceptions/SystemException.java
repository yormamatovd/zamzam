package product.exceptions;

import product.enums.ApiStatus;

public class SystemException extends RuntimeException {
    public SystemException(ApiStatus status) {
        super(status.name());
    }
}

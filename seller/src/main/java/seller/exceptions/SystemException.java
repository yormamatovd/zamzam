package seller.exceptions;

import seller.enums.ApiStatus;

public class SystemException extends RuntimeException {
    public SystemException(ApiStatus status) {
        super(status.name());
    }
}

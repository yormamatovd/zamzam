package seller.exceptions;

import seller.enums.ApiStatus;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}

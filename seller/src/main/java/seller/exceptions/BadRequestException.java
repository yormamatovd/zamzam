package seller.exceptions;

import seller.enums.ApiStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(ApiStatus status) {
        super(status.name());
    }
}

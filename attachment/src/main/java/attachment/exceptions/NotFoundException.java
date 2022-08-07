package attachment.exceptions;

import attachment.enums.ApiStatus;

public class NotFoundException extends RuntimeException{

    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}

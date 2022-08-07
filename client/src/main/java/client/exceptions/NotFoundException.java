package client.exceptions;

import client.enums.ApiStatus;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ApiStatus status) {
        super(status.name());
    }
}

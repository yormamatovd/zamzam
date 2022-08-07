package client.exceptions;

import client.enums.ApiStatus;

public class BadRequestException extends RuntimeException {

    public BadRequestException(ApiStatus status) {
        super(status.name());
    }
}

package client.exceptions;

import client.enums.ApiStatus;

public class SystemException extends RuntimeException{
    public SystemException(ApiStatus status) {
        super(status.name());
    }
}

package com.spa.springCommuProject.common.exception;

import com.amazonaws.services.kms.model.NotFoundException;

public class NotFoundSelectException extends NotFoundException {
    /**
     * Constructs a new NotFoundException with the specified error message.
     *
     * @param message Describes the error encountered.
     */
    public NotFoundSelectException(String message) {
        super(message);
    }
}

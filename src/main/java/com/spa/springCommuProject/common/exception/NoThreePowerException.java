package com.spa.springCommuProject.common.exception;

import com.amazonaws.services.kms.model.NotFoundException;

public class NoThreePowerException extends NotFoundException {
    public NoThreePowerException(String message) {
        super(message);
    }
}

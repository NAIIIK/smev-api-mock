package com.javarush.rodionov.smevapimock.model;

import lombok.Getter;

@Getter
public class CheckResponse {
    private final boolean isValid;
    private final String status;
    private final String decodedStatus;

    public CheckResponse(boolean valid) {
        this.isValid = valid;

        if (valid) {
            this.status = "300";
            this.decodedStatus = "Passport is valid";
        } else {
            this.status = "301";
            this.decodedStatus = "Passport is invalid";
        }
    }
}

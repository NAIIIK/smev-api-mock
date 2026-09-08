package com.example.smevapimock.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CheckResponse {
    public boolean isValid = true;
    public String status = "300";
    public String decodeDocStatus = "Passport is valid";
    public String issuerCode = null;
    public String issueDate = null;
    public String invalidityReason = null;
    public String decodeInvalidityReason = null;
    public String invaliditySince = null;

    public Result result = new Result();

    public static class Result {
        public boolean success = true;
        public String errorCode = "";
        public String description = "";
    }
}

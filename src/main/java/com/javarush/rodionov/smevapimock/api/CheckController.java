package com.javarush.rodionov.smevapimock.api;

import com.javarush.rodionov.smevapimock.model.CheckRequest;
import com.javarush.rodionov.smevapimock.model.CheckResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequiredArgsConstructor
@RequestMapping("/smev-api/v1")
public class CheckController {

    @PostMapping(
            value = "/CheckPassport"
//            consumes = MediaType.APPLICATION_JSON_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE
    )
    // http://localhost:8081/api/check/v1/CheckPassport
    public ResponseEntity<CheckResponse> check(
//            @RequestHeader(value = "x-token", required = false) String token,
            @RequestBody CheckRequest req
    ) {
        boolean valid = ThreadLocalRandom.current().nextBoolean();

        CheckResponse body = new CheckResponse();
        if (valid) {
            body.isValid = true;
            body.status = "300";
            body.decodeDocStatus = "Паспорт действителен";
        } else {
            body.isValid = false;
            body.status = "301";
            body.decodeDocStatus = "Паспорт недействителен";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json; charset=utf-8");
        headers.add("date", "Mon,01 Sep 2025 11:55:38 GMT");
        headers.add("server", "Microsoft-IIS/10.0");
        headers.add("x-powered-by", "ASP.NET");

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}

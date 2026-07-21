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

    @PostMapping("/check")
    public ResponseEntity<CheckResponse> check(@RequestBody CheckRequest checkRequest){
        boolean valid = ThreadLocalRandom.current().nextBoolean();

        CheckResponse body = new CheckResponse(valid);

        HttpHeaders headers = new HttpHeaders();
        headers.add("content-type", "application/json");
        headers.add("date", Instant.now().toString());
        headers.add("server", "Microsoft-IIS/10.0");
        headers.add("x-powered-by", "ASP.NET");

        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }
}

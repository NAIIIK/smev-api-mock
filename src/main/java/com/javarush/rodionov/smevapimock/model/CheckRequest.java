package com.javarush.rodionov.smevapimock.model;

public record CheckRequest(
        String familyName,
        String firstName,
        String patronymic,
        String series,
        String number
) {}

package com.example.smevapimock.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CheckRequest {
    private String familyName;
    private String firstName;
    private String patronymic;
    private String series;
    private String number;
}

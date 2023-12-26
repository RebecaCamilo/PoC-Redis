package com.example.pocredis.model;

import com.example.pocredis.controller.request.AnyObjectRequest;

public class AnyObjectRequestFactoryTest {

    public static AnyObjectRequest createValidAnyObjectRequest() {
        return AnyObjectRequest.builder()
                .description("mesa")
                .quantity(1)
                .build();
    }


    public static AnyObjectRequest createInvalidAnyObjectNullDescription() {
        return AnyObjectRequest.builder()
                .quantity(1)
                .build();
    }

    public static AnyObjectRequest createInvalidAnyObjectMaxCharacters() {
        return AnyObjectRequest.builder()
                .description("cadeiracadeiracadeiracadeiracadeiracadeiracadeiracadeira")
                .quantity(1)
                .build();
    }
}

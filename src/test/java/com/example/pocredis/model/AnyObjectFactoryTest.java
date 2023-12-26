package com.example.pocredis.model;

public class AnyObjectFactoryTest {

    public static AnyObject createValidAnyObject() {
        return AnyObject.builder()
                .id(1L)
                .description("mesa")
                .quantity(1)
                .build();
    }

}

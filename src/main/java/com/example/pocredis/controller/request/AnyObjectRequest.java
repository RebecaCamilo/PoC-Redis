package com.example.pocredis.controller.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnyObjectRequest {

    @NotBlank(message = "Description is mandatory.")
    @Max(value = 5, message = "Max number of characters is 5")
    String description;
}

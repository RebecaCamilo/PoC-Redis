package com.example.pocredis.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class AnyObjectRequest {

    @NotBlank(message = "Description is mandatory.")
    @Size(max = 5, message = "Max number of characters is 5")
    String description;

    int quantity;
}

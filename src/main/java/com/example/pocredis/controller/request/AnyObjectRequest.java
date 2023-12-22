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
    @Size(max = 50, message = "Maximum number of characters must be 50")
    String description;

    int quantity;
}

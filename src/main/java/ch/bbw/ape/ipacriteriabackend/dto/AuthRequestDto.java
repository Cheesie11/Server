package ch.bbw.ape.ipacriteriabackend.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthRequestDto(
        @NotBlank String firstname,
        @NotBlank String lastname
) {}


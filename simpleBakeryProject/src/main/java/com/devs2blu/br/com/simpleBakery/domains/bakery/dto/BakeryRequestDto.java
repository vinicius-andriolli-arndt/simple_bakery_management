package com.devs2blu.br.com.simpleBakery.domains.bakery.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BakeryRequestDto {
    @NotNull(message = "O nome não pode ser nulo")
    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    private String address;

    @NotNull(message = "O número de telefone não pode ser nulo")
    @NotBlank(message = "O número de telefone não pode ser vazio")
    @Size(min = 9, max = 16, message = "O número de telefone deve ter entre 9 e 16 caracteres")
    @JsonProperty("phone_number")
    private String phoneNumber;
}
